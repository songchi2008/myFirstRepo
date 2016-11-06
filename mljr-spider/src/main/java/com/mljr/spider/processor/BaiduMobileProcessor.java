/**
 * 
 */
package com.mljr.spider.processor;

import java.util.Arrays;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Splitter;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Selectable;

/**
 * @author Ckex zha </br>
 *         2016年11月6日,下午8:34:51
 *
 */
public class BaiduMobileProcessor extends AbstractPageProcessor {

	private Site site = Site.me().setRetrySleepTime(1500).setRetryTimes(3).setUserAgent(
			"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");

	public BaiduMobileProcessor() {
		super();
	}

	@Override
	public void process(Page page) {
		Selectable div = page.getHtml().xpath("//div[@class='op_mobilephone_r']");
		if (div.match()) {
			page.putField("电话号码", div.xpath("//span[1]/text()"));
			String location = div.xpath("//span[2]/text()").get();
			List<String> res = Splitter.onPattern("  ").omitEmptyStrings().trimResults().splitToList(location);
			page.putField("type", res.get(0));
			page.putField("city", res.get(1));
		} else {
			JSONObject jsonObject = JSON.parseObject(page.getJson().get());
			JSONArray dataArray = jsonObject.getJSONArray("data");
			if (dataArray != null && !dataArray.isEmpty()) {
				JSONObject dataObj = (JSONObject) dataArray.get(0);
				page.putField("电话号码", dataObj.get("phoneno"));
				page.putField("type", dataObj.get("type"));
				page.putField("city", dataObj.get("city"));
			}
		}
	}

	@Override
	public Site getSite() {
		return this.site;
	}

}
