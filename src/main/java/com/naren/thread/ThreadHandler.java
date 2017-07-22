package com.naren.thread;

/**
 * Custom Thread Exception handler
 * @author ntanwa
 *
 */
public class ThreadHandler implements Thread.UncaughtExceptionHandler{

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		System.out.println(t.getName()+" Error Message : "+e.getMessage());
		
	}
	
}