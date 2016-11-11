/**
 * 
 */
package com.mljr.spider.processor;

import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

/**
 * @author Ckex zha </br>
 *         2016年11月10日,上午11:33:08
 *
 */
public class JuheMobileProcessor extends AbstractPageProcessor {

	private Site site = Site.me().setDomain("juhe.cn").setSleepTime(10);

	public JuheMobileProcessor() {
		super();
	}

	@Override
	public void process(Page page) {
		String text = page.getJson().get();
		JSONObject jsonObject = JSON.parseObject(text);
		Integer retCode = jsonObject.getInteger("error_code");
		if (retCode != null && retCode.intValue() == 0) {
			page.putField(page.getUrl().get(), text);
			return;
		}
		List<NameValuePair> params = URLEncodedUtils.parse(page.getUrl().get(), Charset.forName("UTF-8"));
		for (NameValuePair nameValuePair : params) {
			if (StringUtils.equalsIgnoreCase(nameValuePair.getName(), "tel")) {
				logger.error("mobile=%s, result=%s", nameValuePair.getValue(), text);
				return;
			}
		}
	}

	@Override
	public Site getSite() {
		return this.site;
	}

}
