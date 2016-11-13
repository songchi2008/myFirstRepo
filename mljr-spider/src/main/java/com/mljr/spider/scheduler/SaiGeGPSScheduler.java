/**
 * 
 */
package com.mljr.spider.scheduler;

import org.apache.commons.lang3.StringUtils;

import com.mljr.spider.request.AbstractRequest;
import com.mljr.spider.request.RestfulReqeust;
import com.mljr.spider.umq.UMQMessage;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;

/**
 * @author Ckex zha </br>
 *         2016年11月8日,下午12:05:27
 *
 */
public class SaiGeGPSScheduler extends AbstractScheduler {

	public static final String URL = "http://218.17.3.228:8008/mljrserver/vehicle/queryGpsInfo";

	private static final String params = "{\"callLetter\":\"\",\"flag\":true,\"sign\":\"335BB919C5476417E424FF6F0BC5AD6F\"}";

	public SaiGeGPSScheduler(Spider spider, String queueId) throws Exception {
		super(spider, queueId);
	}

	@Override
	public void push(Request request, Task task) {
		put(request);
	}

	@Override
	public Request poll(Task task) {
		return take();
	}

	@Override
	public int getLeftRequestsCount(Task task) {
		return 0;
	}

	@Override
	public int getTotalRequestsCount(Task task) {
		return 0;
	}

	private void pushTask(Spider spider) {
		RestfulReqeust request = new RestfulReqeust(URL, params);
		request.setMethod(AbstractRequest.POST);
		this.push(request, spider);
	}

	@Override
	public boolean pushTask(Spider spider, UMQMessage message) {
		boolean isGps = StringUtils.startsWithIgnoreCase(message.msgData.getMsgBody(), "gps");
		if (isGps) {
			pushTask(spider);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("process msg " + isGps + "," + message);
		}
		// skip
		return true;
	}

}
