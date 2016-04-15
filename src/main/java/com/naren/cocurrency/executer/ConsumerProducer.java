/**
 * 
 */
package com.naren.cocurrency.executer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author narender
 *
 */
public class ConsumerProducer {

	/**
	 * 
	 */
	public ConsumerProducer() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BlockingQueue<String> queue=new LinkedBlockingQueue<String>();
		Thread thread1=new Thread(new Consumer(queue,"Consumer"));
		Thread thread2=new Thread(new Producer(queue,"Producer"));
		thread2.start();
		thread1.start();
		
	}

}
