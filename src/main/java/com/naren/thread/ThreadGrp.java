/**
 * 
 */
package com.naren.thread;

import java.util.concurrent.TimeUnit;

/**
 * @author ntanwa
 *
 */
public class ThreadGrp {

	{
		registerShutDownHook();
	}

	/**
	 * 
	 */
	private void registerShutDownHook() {
		Runnable shutDownTask = () -> {
			System.out.println("Shutting Down...");
		};
		Runtime.getRuntime().addShutdownHook(new Thread(shutDownTask));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		threadGrpRun();
		threadProps();
		new ThreadGrp().synchMethods();
		System.exit(0);
	}

	/**
	 * @throws InterruptedException
	 */
	private void synchMethods(){
		
		//Non stataic method can not acure lock on class
		//Notify always have to be in synchronized block or method
		//wait throw IllegalMonitor Exception on compile time and runtime if called outside of sunchronized method or block
		
		try {
			synchronized(this){
				wait();
			}
		} catch (InterruptedException e) {
			System.out.println("Error : "+e.getMessage());
		}
		//synchronized (this) {
			notify();
		//}
	}

	/**
	 * 
	 */
	private static void threadProps() {
		try {
			Thread th = new Thread(() -> {
				System.out.println("Hllo");
			});
			th.start();
			th.start();
		} catch (Exception e) {
			System.out.println("Exception on call strart 2 time on same thread object"+e);
		}
		StringBuffer buffer =  new StringBuffer("String pros :::::::::");
		buffer.append("\n");
		buffer.append("Is current thread holdslock on this :"+Thread.holdsLock(ThreadGrp.class));
		buffer.append("\n");
		buffer.append("Active thread "+Thread.activeCount());
		buffer.append("\n");
		buffer.append("Name"+Thread.currentThread().getName());
		buffer.append("\n");
		buffer.append("Id "+Thread.currentThread().getId());
		buffer.append("\n");
		buffer.append("Is Intrupted this is a native mathod "+Thread.currentThread().isInterrupted());
		buffer.append("\n");
		buffer.append("Thread group"+Thread.currentThread().getThreadGroup());
		buffer.append("\n");
		buffer.append("is alive  "+Thread.currentThread().isAlive());
		buffer.append("\n");
		buffer.append("Intrupted "+Thread.currentThread().interrupted());
		buffer.append("\n");
		buffer.append("Exception Handler"+Thread.getDefaultUncaughtExceptionHandler());
		System.out.println(buffer);
	}

	/**
	 * 
	 */
	private static void threadGrpRun() {
		ThreadGroup tg1 = createThreadGrp();

		try {
			System.out.println("Sleeping for 2 Sec..");
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			System.out.println("Error : " + e.getMessage());
		}
		tg1.setDaemon(true);
		tg1.interrupt();
	}

	/**
	 * @return
	 */
	private static ThreadGroup createThreadGrp() {
		ThreadGroup tg1 = new ThreadGroup("Group A");
		Runnable run = () -> {
			System.out.println("Thread : " + Thread.currentThread().getName() + " started.");
			while (!Thread.interrupted()) {
				// System.out.println("Happ");
			}
			if (Thread.interrupted()) {
				System.out.println("Thread : " + Thread.currentThread().getName() + " inturepted.");
			}
		};
		new Thread(tg1, run, "one").start();
		new Thread(tg1, run, "two").start();
		new Thread(tg1, run, "three").start();
		return tg1;
	}

}
