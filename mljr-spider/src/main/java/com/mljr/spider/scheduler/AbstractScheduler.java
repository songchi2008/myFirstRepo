/**
 * 
 */
package com.mljr.spider.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.scheduler.Scheduler;

/**
 * @author Ckex zha </br>
 *         2016年11月7日,上午11:37:00
 *
 */
public abstract class AbstractScheduler implements Scheduler {

	protected transient Logger logger = LoggerFactory.getLogger(getClass());

	public AbstractScheduler() {
		super();
	}

}
