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
public class Producer implements Runnable {

	private static final Logger LOG = Logger.getLogger(Producer.class);
	private BlockingQueue<String> queue;
	private String name;

	public Producer(BlockingQueue<String> queue, String name) {
		this.queue = queue;
		this.name = name;
	}

	public void run() {
		try {
			for (int i = 0; i < 10; i++) {
				queue.put(String.valueOf(i));
				LOG.debug(name + " " + String.valueOf(i));
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
