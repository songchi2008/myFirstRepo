/**
 * 
 */
package com.mljr.spider.scheduler;

import com.ucloud.umq.model.Message;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;

/**
 * @author Ckex zha </br>
 *         2016年11月10日,上午11:37:15
 *
 */
public class JuheMobileScheduler extends AbstractScheduler {

	public static final String URL = "http://op.juhe.cn/onebox/phone/query?tel=%s&key=d3baaded0db0ea2dd0a359fb485e3d60";

	public JuheMobileScheduler(Spider spider, ConsumerMessage consumerMessage) {
		super(spider, consumerMessage);
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

	@Override
	public boolean pushTask(Spider spider, Message message) {
		String url = String.format(URL, message.getMsgBody());
		push(new Request(url), spider);
		return true;
	}

}
