/**
 * 
 */
package com.mljr.spider.scheduler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mljr.spider.umq.UMQClient;
import com.mljr.spider.umq.UMQMessage;
import com.ucloud.umq.action.MessageData;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.MonitorableScheduler;
import us.codecraft.webmagic.scheduler.Scheduler;

/**
 * @author Ckex zha </br>
 *         2016年11月7日,上午11:37:00
 *
 */
public abstract class AbstractScheduler implements Scheduler, MonitorableScheduler {

	protected transient Logger logger = LoggerFactory.getLogger(getClass());

	private static final int QUEUE_SIZE = 15;

	private static final AtomicLong count = new AtomicLong();

	private final int THREAD_SIZE = 1;
	protected final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(THREAD_SIZE,
			new ThreadFactory() {

				@Override
				public Thread newThread(Runnable r) {
					return new Thread(r, "spider-" + getClass() + "-work-" + count.incrementAndGet());
				}
			});

	public abstract boolean pushTask(Spider spider, UMQMessage message);

	private BlockingQueue<Request> blockQueue = new LinkedBlockingQueue<Request>(QUEUE_SIZE);

	public AbstractScheduler(final Spider spider, final String qid) throws Exception {
		super();
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
		// executor.scheduleWithFixedDelay(new Runnable() {
		//
		// @Override
		// public void run() {
		// for (;;) {
		// try {
		// Message message = consumerMessage.getMessage();
		// if (message == null) {
		// break; // Nothing.
		// }
		// if (pushTask(spider, message)) {
		// if (Math.random() * 100 < 1) {
		// if (logger.isDebugEnabled()) {
		// logger.debug("Get message ==> " + message.getMsgBody());
		// }
		// }
		// consumerMessage.confirmMsg(message);
		// } else {
		// logger.error("Push task erro ." + message.getMsgId() + ", " +
		// message.getMsgBody());
		// }
		// } catch (Throwable e) {
		// logger.error("Exception. " + ExceptionUtils.getStackTrace(e));
		// }
		// }
		// }
		// }, 0, 1, TimeUnit.SECONDS);
	}

	private void subscribeQueue(final Spider spider, final String qid) throws Exception {
		logger.debug("subscribeQueue = " + qid);
		UMQClient.getInstence().subscribeQueue(UMQClient.getInstence().new MessageHandler(qid) {

			@Override
			public boolean processMsg(MessageData message) {
				if (pushTask(spider, new UMQMessage(message))) {
					if (Math.random() * 100 < 1) {
						if (logger.isDebugEnabled()) {
							logger.debug("Get message ==> " + message.getMsgBody());
						}
					}
				}
				return true;
			}
		});
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
