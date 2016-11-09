/**
 * 
 */
package com.mljr.spider.scheduler.manager;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mljr.spider.downloader.RestfulDownloader;
import com.mljr.spider.processor.SaiGeGPSProcessor;
import com.mljr.spider.scheduler.AbstractScheduler.ConsumerMessage;
import com.mljr.spider.scheduler.SaiGeGPSScheduler;
import com.mljr.spider.umq.UMQClient;
import com.ucloud.umq.common.ServiceAttributes;
import com.ucloud.umq.model.Message;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

/**
 * @author Ckex zha </br>
 *         2016年11月9日,下午3:27:45
 *
 */
public class Manager {

	protected transient Logger logger = LoggerFactory.getLogger(getClass());

	private static final int MAX_THREAD = 3;
	private static final AtomicLong count = new AtomicLong();

	public Manager() {
		super();
	}

	protected static final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(MAX_THREAD,
			new ThreadFactory() {

				@Override
				public Thread newThread(Runnable r) {
					return new Thread(r, "spider-work-" + count.incrementAndGet());
				}
			});

	public void run() {
		startSaiGeGPS();
	}

	// 赛格GPS数据
	private void startSaiGeGPS() {
		final String queueId = "umq-luj3bt";
		final Spider spider = Spider.create(new SaiGeGPSProcessor()).setDownloader(new RestfulDownloader()).thread(3)
				.addPipeline(new ConsolePipeline());
		spider.setScheduler(new SaiGeGPSScheduler(spider, new ConsumerMessage() {

			@Override
			public Message getMessage() {
				return getUMQMessage(queueId);
			}

			@Override
			public void confirmMsg(Message msg) {
				aksMessage(queueId, msg.getMsgId());
			}

		}));
		spider.runAsync();
		logger.info("Start SaiGeGPSProcessor finished. ");
	}

	private Message getUMQMessage(String queueId) {
		Message message = null;
		try {
			message = UMQClient.getInstence().getMessage(ServiceAttributes.getRegion(), ServiceAttributes.getRole(),
					queueId);
			if (logger.isDebugEnabled()) {
				logger.debug("pull message." + message);
			}
		} catch (Throwable e) {
			logger.error("UMQ pull message Error," + ExceptionUtils.getStackTrace(e));
		}
		return message;
	}

	private void aksMessage(String queueId, String msgId) {
		boolean succ = false;
		try {
			succ = UMQClient.getInstence().ackMsg(ServiceAttributes.getRegion(), ServiceAttributes.getRole(), queueId,
					msgId);
		} catch (Exception e) {
			logger.error("Ask message Error, " + ExceptionUtils.getStackTrace(e));
		}
		if (!succ) {
			logger.warn("Ask message false," + queueId + " - " + msgId);
		}

	}
}
