/**
 * 
 */
package com.mljr.spider.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

/**
 * @author Ckex zha </br>
 *         2016年11月7日,下午5:20:39
 *
 */
public class SaiGeGPSProcessor extends AbstractPageProcessor {

	protected transient Logger gps = LoggerFactory.getLogger("gps-data");

	private Site site = Site.me().setDomain("saige-gps");

	public SaiGeGPSProcessor() {
		super();
	}

	@Override
	public void process(Page page) {
		page.putField(page.getUrl().get(), page.getJson().get());
	}

	@Override
	public Site getSite() {
		return this.site;
	}

}
