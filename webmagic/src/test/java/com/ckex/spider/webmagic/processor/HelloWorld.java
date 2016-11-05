package com.ckex.spider.webmagic.processor;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

public class HelloWorld {

	private static final String URL = "http://www.114huoche.com/shouji/15601662655";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String url = URL;
		Spider spider = Spider.create(new Huoche114Processor()).addUrl(url).addPipeline(new ConsolePipeline());
		spider.runAsync();
	}
}
