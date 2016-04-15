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
public class Consumer implements Runnable{

	/**
	 * 
	 */
	private static final Logger LOG=Logger.getLogger(Consumer.class);
	private BlockingQueue<String>  queue;
	private String name;

	public BlockingQueue<String> getQueue() {
		return queue;
	}

	public void setQueue(BlockingQueue<String> queue) {
		this.queue = queue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public Consumer(BlockingQueue<String>  queue,String name) {
		this.queue=queue;
		this.name=name;
	}
	public void run() {
		while(true){
			try {
				LOG.debug(name+" "+queue.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
