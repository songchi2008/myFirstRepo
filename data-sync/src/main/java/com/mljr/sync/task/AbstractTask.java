/**
 * 
 */
package com.mljr.sync.task;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;

/**
 * @author Ckex zha </br>
 *         2016年11月28日,下午12:01:55
 *
 */
public abstract class AbstractTask implements Runnable {

	protected static transient Logger logger = LoggerFactory.getLogger(AbstractTask.class);

	public AbstractTask() {
		super();
	}

	abstract void execute();

	@Override
	public void run() {
		Stopwatch watch = Stopwatch.createStarted();
		try {
			execute();
			watch.stop();
			logger.info("execute task, use time:" + watch.elapsed(TimeUnit.MILLISECONDS));
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("execute task error. " + ExceptionUtils.getStackTrace(ex));
		}
	}

}
