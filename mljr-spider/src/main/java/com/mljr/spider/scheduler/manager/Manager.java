/**
 * 
 */
package com.mljr.spider.scheduler.manager;

import java.net.URISyntaxException;

import com.google.common.collect.Lists;
import com.mljr.spider.downloader.RestfulDownloader;
import com.mljr.spider.listener.DownloaderSpiderListener;
import com.mljr.spider.processor.JuheMobileProcessor;
import com.mljr.spider.processor.SaiGeGPSProcessor;
import com.mljr.spider.scheduler.JuheMobileScheduler;
import com.mljr.spider.scheduler.SaiGeGPSScheduler;
import com.mljr.spider.storage.LogPipeline;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.SpiderListener;

/**
 * @author Ckex zha </br>
 *         2016年11月9日,下午3:27:45
 *
 */
public class Manager extends AbstractMessage {

	private final SpiderListener listener = new DownloaderSpiderListener();

	public Manager() {
		super();
	}

	public void run() throws Exception {
		startSaiGeGPS();
		startJuheMobile();
	}

	// 聚合手机标签
	private void startJuheMobile() throws Exception {
		JuheMobileProcessor processor = new JuheMobileProcessor();
		LogPipeline pipeline = new LogPipeline(JUHE_MOBILE_LOG_NAME);
		final Spider spider = Spider.create(processor).addPipeline(pipeline);
		spider.setSpiderListeners(Lists.newArrayList(listener));
		spider.setExecutorService(THREAD_POOL);
		spider.setScheduler(new JuheMobileScheduler(spider, JUHE_MOBILE_QUEUE_ID));
		spider.runAsync();
		logger.info("Start JuheMobileProcessor finished. ");
	}

	// 赛格GPS数据
	private void startSaiGeGPS() throws Exception {
		final Spider spider = Spider.create(new SaiGeGPSProcessor()).setDownloader(new RestfulDownloader())
				.addPipeline(new LogPipeline(GPS_LOG_NAME));
		spider.setSpiderListeners(Lists.newArrayList(listener));
		spider.setExecutorService(THREAD_POOL);
		spider.setScheduler(new SaiGeGPSScheduler(spider, GPS_QUEUE_ID));
		spider.runAsync();
		logger.info("Start SaiGeGPSProcessor finished. ");
	}
}
