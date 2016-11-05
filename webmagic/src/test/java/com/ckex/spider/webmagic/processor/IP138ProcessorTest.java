/**
 * 
 */
package com.ckex.spider.webmagic.processor;

import java.util.concurrent.TimeUnit;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.scheduler.Scheduler;

/**
 * @author ckex
 *
 */
public class IP138ProcessorTest {

	private static final String URL = "http://www.ip138.com:8080/search.asp?mobile=%s&action=mobile";

	public IP138ProcessorTest() {
		super();
	}

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		String url = String.format(URL, "15601662655");
		Spider spider = Spider.create(new IP138Processor()).thread(5).addUrl(url).addPipeline(new ConsolePipeline());
		spider.runAsync();
		Scheduler scheduler = spider.getScheduler();
		for (int i = 0; i < 1000; i++) {
			url = String.format(URL, 1560 + "" + i + "88");
			scheduler.push(new Request(url), spider);
			TimeUnit.MILLISECONDS.sleep(10);
		}
	}

}
