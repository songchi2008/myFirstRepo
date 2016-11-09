/**
 * 
 */
package com.mljr.spider.scheduler;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

import com.mljr.spider.request.AbstractRequest;
import com.mljr.spider.request.RestfulReqeust;
import com.ucloud.umq.model.Message;

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

	private ConcurrentLinkedQueue<Request> queue = new ConcurrentLinkedQueue<Request>();

	public SaiGeGPSScheduler(Spider spider, ConsumerMessage consumerMessage) {
		super(spider, consumerMessage);
	}

	@Override
	public void push(Request request, Task task) {
		queue.add(request);
		logger.debug("push ..." + request);
	}

	@Override
	public Request poll(Task task) {
		while (true) {
			Request res = queue.poll();
			if (res == null) {
				try {
					TimeUnit.SECONDS.sleep(10);
					logger.info("queue is empty.");
				} catch (InterruptedException e) {
				}
			} else {
				return res;
			}
		}
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
	public boolean pushTask(Spider spider, Message message) {
		pushTask(spider);
		return true;
	}

}
