/**
 * 
 */
package com.ckex.spider.webmagic.processor;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

/**
 * @author Ckex zha </br>
 *         2016年11月5日,下午9:18:30
 *
 */
public class Huoche114ProcessorTests {

	private static final String URL = "http://www.114huoche.com/shouji/15601662655";

	public Huoche114ProcessorTests() {
		super();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String url = URL;
		Spider spider = Spider.create(new Huoche114Processor()).addUrl(url).addPipeline(new ConsolePipeline());
		spider.runAsync();
	}

}
