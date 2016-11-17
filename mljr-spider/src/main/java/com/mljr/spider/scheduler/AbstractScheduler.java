/**
 * 
 */
package com.mljr.spider.scheduler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.MonitorableScheduler;
import us.codecraft.webmagic.scheduler.Scheduler;

import com.mljr.spider.mq.UMQClient;
import com.mljr.spider.mq.UMQMessage;
import com.mljr.spider.scheduler.manager.AbstractMessage.PullMsgTask;
import com.ucloud.umq.action.MessageData;

/**
 * @author Ckex zha </br> 2016年11月7日,上午11:37:00
 *
 */
public abstract class AbstractScheduler implements Scheduler, MonitorableScheduler {

  protected transient Logger logger = LoggerFactory.getLogger(getClass());

  private static final int QUEUE_SIZE = 15;

  private static final AtomicLong count = new AtomicLong();

  private final int THREAD_SIZE = 1; // must 1
  protected final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(THREAD_SIZE, new ThreadFactory() {

    @Override
    public Thread newThread(Runnable r) {
      return new Thread(r, "sp-" + logger.getName() + "-work-" + count.incrementAndGet());
    }
  });

  public abstract boolean pushTask(Spider spider, UMQMessage message);

  private BlockingQueue<Request> blockQueue = new LinkedBlockingQueue<Request>(QUEUE_SIZE);

  public AbstractScheduler(final Spider spider, BlockingQueue<UMQMessage> mqMsgQueue) throws Exception {
    super();
    pullLocalQueue(spider, mqMsgQueue);
  }

  public AbstractScheduler(final Spider spider, final PullMsgTask task) throws Exception {
    super();
    pullMsgTask(spider, task);
  }

  public AbstractScheduler(final Spider spider, final String qid) throws Exception {
    super();
    subscribeMsg(spider, qid);
  }

  private void pullLocalQueue(final Spider spider, final BlockingQueue<UMQMessage> localQueue) {
    startTask(new Runnable() {

      @Override
      public void run() {
        for (;;) {
          UMQMessage message = localQueue.poll();
          if (message == null) {
            break;
          }
          sentMsg(spider, message);
        }
      }
    });
  }

  private void pullMsgTask(final Spider spider, final PullMsgTask task) {
    final AtomicInteger id = new AtomicInteger(0);
    startTask(new Runnable() {

      @Override
      public void run() {
        for (;;) {
          String message = task.pullMsg();
          if (StringUtils.isBlank(message)) {
            break;
          }
          UMQMessage msg = new UMQMessage(String.valueOf(Math.abs(id.incrementAndGet())), message);
          sentMsg(spider, msg);
        }
      }
    });
  }

  private void startTask(Runnable r) {
    executor.scheduleWithFixedDelay(r, 0, 1, TimeUnit.SECONDS);
  }

  private void subscribeMsg(final Spider spider, final String qid) throws InterruptedException {
    final CountDownLatch downLatch = new CountDownLatch(1);
    final AtomicBoolean subscribe = new AtomicBoolean(false);
    executor.execute(new Runnable() {

      @Override
      public void run() {
        try {
          logger.debug("Start subscribe queue, " + qid);
          subscribeQueue(spider, qid);
          boolean ret = subscribe.getAndSet(true);
          logger.debug(ret + " Started subscribe queue, " + qid);
        } catch (Throwable e) {
          logger.error("Subscribe error. " + ExceptionUtils.getStackTrace(e));
        } finally {
          downLatch.countDown();
        }
      }
    });
    downLatch.await();
    if (!subscribe.get()) {
      throw new RuntimeException("Subscribe queue " + subscribe.get());
    }
  }

  private void subscribeQueue(final Spider spider, final String qid) throws Exception {
    logger.debug("subscribeQueue = " + qid);
    UMQClient.getInstence().subscribeQueue(UMQClient.getInstence().new MessageHandler(qid) {

      @Override
      public boolean processMsg(MessageData message) {
        return sentMsg(spider, new UMQMessage(message));
      }

    });
  }


  private boolean sentMsg(Spider spider, UMQMessage message) {
    if (pushTask(spider, message)) {
      if (Math.random() * 100 < 1) {
        if (logger.isDebugEnabled()) {
          logger.debug("Get message ==> " + message.toString());
        }
      }
      return true;
    }
    return false;
  }

  // 阻塞队列
  protected void put(Request request) {
    try {
      blockQueue.put(request);
    } catch (InterruptedException e) {
      if (logger.isDebugEnabled()) {
        e.printStackTrace();
      }
      logger.error("Push task error. " + ExceptionUtils.getStackTrace(e));
    }
  }

  // 阻塞队列
  protected Request take() {
    for (;;) {
      try {
        return blockQueue.take();
      } catch (InterruptedException e) {
        if (logger.isDebugEnabled()) {
          e.printStackTrace();
        }
        logger.error("Push task error. " + ExceptionUtils.getStackTrace(e));
      }
    }
  }

}
