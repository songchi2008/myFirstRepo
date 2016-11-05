/**
 * 
 */
package com.ckex.spider.webmagic.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

/**
 * @author ckex
 *
 */
public class IP138ProcessorTests implements PageProcessor {

	private Site site = Site.me().setUserAgent(
			"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");

	@Override
	public void process(Page page) {
		// String html = page.getHtml().get();
		// Parser parser = Parser.createParser(html, "UTF-8");
		// System.out.println(page.getResultItems().toString());
		// page.putField("text", page.getHtml().$("TD.tdc2").all());
		Selectable tbody = page.getHtml().xpath("//body//table[2]/tbody");
		page.putField("tbody", tbody);
		page.putField("location", tbody.xpath("//tr[3]//td[2]//text()"));
		page.putField("type", tbody.xpath("//tr[4]//td[2]//text()"));
		page.putField("3", tbody.xpath("//tr[5]//td[2]//text()"));
		page.putField("4", tbody.xpath("//tr[6]//td[2]//text()"));
	}

	@Override
	public Site getSite() {
		return this.site;
	}

	private static final String URL = "http://www.ip138.com:8080/search.asp?mobile=15601662655&action=mobile";

	public static void main(String[] args) {
		Spider.create(new IP138Processor()).addUrl(URL).addPipeline(new ConsolePipeline()).run();
	}
}
