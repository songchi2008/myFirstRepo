/**
 * 
 */
package com.mljr.spider.main;

import com.mljr.spider.scheduler.manager.Manager;

/**
 * @author Ckex zha </br>
 *         2016年11月6日,上午12:04:50
 *
 */
public class Main {

	public Main() {
		super();
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
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
