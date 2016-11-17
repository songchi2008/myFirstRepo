/**
 * 
 */
package com.mljr.spider.scheduler.manager;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.SpiderListener;
import us.codecraft.webmagic.pipeline.FilePipeline;

import com.google.common.collect.Lists;
import com.mljr.spider.downloader.RestfulDownloader;
import com.mljr.spider.listener.DownloaderSpiderListener;
import com.mljr.spider.mq.DistributionMessage;
import com.mljr.spider.mq.UMQMessage;
import com.mljr.spider.processor.BaiduMobileProcessor;
import com.mljr.spider.processor.JuheMobileProcessor;
import com.mljr.spider.processor.SaiGeGPSProcessor;
import com.mljr.spider.scheduler.AbstractScheduler;
import com.mljr.spider.scheduler.BaiduMobileScheduler;
import com.mljr.spider.scheduler.JuheMobileScheduler;
import com.mljr.spider.scheduler.SaiGeGPSScheduler;
import com.mljr.spider.storage.LogPipeline;

/**
 * @author Ckex zha </br> 2016年11月9日,下午3:27:45
 *
 */
public class Manager extends AbstractMessage {

  private static final int QUEUE_SIZE = 10;
  private static final String FILE_PATH = "/data/html";
  private final SpiderListener listener = new DownloaderSpiderListener();

  public Manager() {
    super();
  }

  public void run() throws Exception {
    DistributionMessage dis = new DistributionMessage(getPullMsgTask(JUHE_MOBILE_RPC_QUEUE_ID));
    startSaiGeGPS();
    startJuheMobile(dis);
    startBaiduMobile(dis);
    dis.start();
  }

  // 聚合手机标签
  private void startJuheMobile(DistributionMessage dis) throws Exception {
    JuheMobileProcessor processor = new JuheMobileProcessor();
    LogPipeline pipeline = new LogPipeline(JUHE_MOBILE_LOG_NAME);
    final Spider spider = Spider.create(processor).addPipeline(pipeline).setDownloader(new RestfulDownloader()).thread(MAX_SIZE + CORE_SIZE);
    spider.setSpiderListeners(Lists.newArrayList(listener));
    spider.setExecutorService(THREAD_POOL);
    // final AbstractScheduler scheduler = new JuheMobileScheduler(spider, JUHE_MOBILE_QUEUE_ID);
    // final AbstractScheduler scheduler = new JuheMobileScheduler(spider,
    // getPullMsgTask(JUHE_MOBILE_RPC_QUEUE_ID));
    final BlockingQueue<UMQMessage> queue = new LinkedBlockingQueue<UMQMessage>(QUEUE_SIZE);
    final AbstractScheduler scheduler = new JuheMobileScheduler(spider, queue);
    spider.setScheduler(scheduler);
    spider.runAsync();
    logger.info("Start JuheMobileProcessor finished. ");
  }

  // 赛格GPS数据
  private void startSaiGeGPS() throws Exception {
    final Spider spider = Spider.create(new SaiGeGPSProcessor()).setDownloader(new RestfulDownloader()).addPipeline(new LogPipeline(GPS_LOG_NAME));
    spider.setSpiderListeners(Lists.newArrayList(listener));
    spider.setExecutorService(THREAD_POOL);
    final AbstractScheduler scheduler = new SaiGeGPSScheduler(spider, getPullMsgTask(GPS_RPC_QUEUE_ID));
    spider.setScheduler(scheduler);
    spider.runAsync();
    logger.info("Start SaiGeGPSProcessor finished. ");
  }

  // 百度手机号标签
  private void startBaiduMobile(DistributionMessage dis) throws Exception {
    final Spider spider = Spider.create(new BaiduMobileProcessor()).thread(MAX_SIZE + CORE_SIZE);
    spider.setSpiderListeners(Lists.newArrayList(listener));
    spider.setExecutorService(THREAD_POOL);
    spider.addPipeline(new FilePipeline(FILE_PATH));
    final BlockingQueue<UMQMessage> queue = new LinkedBlockingQueue<UMQMessage>(QUEUE_SIZE);
    final AbstractScheduler scheduler = new BaiduMobileScheduler(spider, queue);
    // final AbstractScheduler scheduler = new BaiduMobileScheduler(spider,
    // getPullMsgTask(JUHE_MOBILE_RPC_QUEUE_ID));
    spider.setScheduler(scheduler);
    spider.runAsync();
    logger.info("Start BaiduMobileProcessor finished. ");
  }



}
