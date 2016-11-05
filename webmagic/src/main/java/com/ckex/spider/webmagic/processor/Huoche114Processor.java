/**
 * 
 */
package com.ckex.spider.webmagic.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 
 * @author Ckex zha </br>
 *         2016年11月5日,下午9:14:35
 *
 */
public class Huoche114Processor implements PageProcessor {

	private Site site = Site.me().setRetrySleepTime(1500).setRetryTimes(3).setUserAgent(
			"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");

	public Huoche114Processor() {
		super();
	}

	@Override
	public void process(Page page) {
		System.out.println(page.getHtml().xpath("//div[@class='xinxi_list']"));
	}

	@Override
	public Site getSite() {
		return this.site;
	}

}
