package com.naren.thread;

import java.util.concurrent.ThreadFactory;

/**
 * Custom Thread Factory
 * @author ntanwa
 *
 */
public class XThreadFactory implements ThreadFactory {

	private ThreadHandler handler;
	
	public XThreadFactory(ThreadHandler handler) {
		this.handler = handler;
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread thread =  new Thread(r);
		thread.setUncaughtExceptionHandler(handler);
		return thread;
	}
	
}