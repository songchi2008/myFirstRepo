/**
 * 
 */
package com.mljr.spider.processor;

import com.mljr.spider.downloader.RestfulDownloader;
import com.mljr.spider.request.AbstractRequest;
import com.mljr.spider.request.RestfulReqeust;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.scheduler.Scheduler;

/**
 * @author Ckex zha </br>
 *         2016年11月7日,下午5:27:56
 *
 */
public class SaiGeGPSProcessorTests {

	private static final String URL = "http://localhost:8080/v1/user";

	private static String params = "{\"user_11111\": {\"Id\": \"user_11111\",\"Username\": \"astaxie\",\"Password\": \"11111\",\"Profile\": {\"Gender\": \"male\",\"Age\": 20,\"Address\": \"Singapore\",\"Email\": \"astaxie@gmail.com\"}}}";

	public SaiGeGPSProcessorTests() {
		super();
	}

	public static void main(String[] args) {
		String url = URL;
		Spider spider = Spider.create(new SaiGeGPSProcessor()).setDownloader(new RestfulDownloader()).thread(5)
				.addPipeline(new ConsolePipeline());
		Scheduler scheduler = spider.getScheduler();
		RestfulReqeust request = new RestfulReqeust(url, params);
		request.setMethod(AbstractRequest.POST);
		scheduler.push(request, spider);
		spider.runAsync();
	}

}
