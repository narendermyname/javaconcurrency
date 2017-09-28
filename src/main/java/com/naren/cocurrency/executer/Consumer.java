/**
 * 
 */
package com.naren.cocurrency.executer;

import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

/**
 * @author narender
 *
 */
public class Consumer implements Runnable {

	/**
	 * 
	 */
	private static final Logger LOG = Logger.getLogger(Consumer.class);
	private BlockingQueue<String> queue;
	private String name;

	public Consumer(BlockingQueue<String> queue, String name) {
		this.queue = queue;
		this.name = name;
	}

	public void run() {
		for (int i = 0; i < 10; i++) {
			try {
				LOG.debug(name + " " + queue.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
