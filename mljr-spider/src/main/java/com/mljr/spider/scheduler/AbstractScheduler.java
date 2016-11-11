/**
 * 
 */
package com.mljr.spider.scheduler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ucloud.umq.model.Message;

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

	private static final int QUEUE_SIZE = 10;
	private static final int MAX_THREAD = 5;
	private static final AtomicLong count = new AtomicLong();

	public interface ConsumerMessage {

		Message getMessage();

		void confirmMsg(Message msg);
	}

	protected static final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(MAX_THREAD,
			new ThreadFactory() {

				@Override
				public Thread newThread(Runnable r) {
					return new Thread(r, "spider-scheduler-work-" + count.incrementAndGet());
				}
			});

	public abstract boolean pushTask(Spider spider, Message message);

	private BlockingQueue<Request> blockQueue = new LinkedBlockingQueue<Request>(QUEUE_SIZE);

	public AbstractScheduler(final Spider spider, final ConsumerMessage consumerMessage) {
		super();
		executor.scheduleWithFixedDelay(new Runnable() {

			@Override
			public void run() {
				for (;;) {
					try {
						Message message = consumerMessage.getMessage();
						if (message == null) {
							break; // Nothing.
						}
						if (pushTask(spider, message)) {
							logger.debug(" get message ==> " + message.getMsgBody());
							consumerMessage.confirmMsg(message);
						} else {
							logger.error("Push task erro ." + message.getMsgId() + ", " + message.getMsgBody());
						}
					} catch (Throwable e) {
						logger.error("Exception. " + ExceptionUtils.getStackTrace(e));
					}
				}
			}
		}, 0, 1, TimeUnit.SECONDS);

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
