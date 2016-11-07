/**
 * 
 */
package com.mljr.spider.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

/**
 * @author Ckex zha </br>
 *         2016年11月7日,下午5:20:39
 *
 */
public class SaiGeGPSProcessor extends AbstractPageProcessor {

	private Site site = Site.me();

	public SaiGeGPSProcessor() {
		super();
	}

	@Override
	public void process(Page page) {
		System.out.println(page.getJson().get());
	}

	@Override
	public Site getSite() {
		return this.site;
	}

}
