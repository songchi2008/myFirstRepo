/**
 * 
 */
package com.mljr.spider.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mljr.spider.scheduler.manager.Manager;

/**
 * @author Ckex zha </br>
 *         2016年11月6日,上午12:04:50
 *
 */
public class Main {

	protected static transient Logger logger = LoggerFactory.getLogger(Main.class);

	public Main() {
		super();
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		logger.info("start...");
		Manager manager = new Manager();
		manager.run();
		synchronized (Main.class) {
			try {
				Main.class.wait();
			} catch (InterruptedException e) {
			}
		}
	}

}
