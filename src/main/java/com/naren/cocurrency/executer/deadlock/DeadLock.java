/**
 * 
 */
package com.naren.cocurrency.executer.deadlock;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author ntanwa
 *
 */
public class DeadLock {

	public static void main(String...a){
		DeadlockDetact deadlockDetector = new DeadlockDetact(new DeadlockConsoleHandler(), 5, TimeUnit.SECONDS);
		deadlockDetector.start();

		final Object lock1 = new Object();
		final Object lock2 = new Object();

		Thread thread1 = new Thread(() -> {
		    synchronized (lock1) {
		      System.out.println("Thread1 acquired lock1");
		      try {
		        TimeUnit.MILLISECONDS.sleep(500);
		      } catch (InterruptedException ignore) {
		      }
		      synchronized (lock2) {
		        System.out.println("Thread1 acquired lock2");
		      }
		    }

		});
		thread1.start();

		Thread thread2 = new Thread(() -> {
		    synchronized (lock2) {
		      System.out.println("Thread2 acquired lock2");
		      synchronized (lock1) {
		        System.out.println("Thread2 acquired lock1");
		      }
		    }
		});
		thread2.start();
	}
}
class DeadlockDetact {

	private DeadlockHandler deadlockHandler;
	private final long period;
	private final TimeUnit unit;
	private final ThreadMXBean mbean = ManagementFactory.getThreadMXBean();
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	final Runnable deadlockCheck = () -> {
		long[] deadlockedThreadIds = mbean.findDeadlockedThreads();

		if (deadlockedThreadIds != null) {
			ThreadInfo[] threadInfos = mbean.getThreadInfo(deadlockedThreadIds);

			deadlockHandler.handleDeadlock(threadInfos);
		}
	};

	public DeadlockDetact(final DeadlockHandler deadlockHandler, final long period, final TimeUnit unit) {
		this.deadlockHandler = deadlockHandler;
		this.period = period;
		this.unit = unit;
	}

	public void start() {
		scheduler.scheduleAtFixedRate(deadlockCheck, period, period, unit);
	}
}

interface DeadlockHandler {
	void handleDeadlock(final ThreadInfo[] deadlockedThreads);
}

class DeadlockConsoleHandler implements DeadlockHandler {

	@Override
	public void handleDeadlock(final ThreadInfo[] deadlockedThreads) {
		if (deadlockedThreads != null) {
			System.err.println("Deadlock detected!");

			Map<Thread, StackTraceElement[]> stackTraceMap = Thread.getAllStackTraces();
			for (ThreadInfo threadInfo : deadlockedThreads) {

				if (threadInfo != null) {

					for (Thread thread : stackTraceMap.keySet()) {

						if (thread.getId() == threadInfo.getThreadId()) {
							System.err.println(threadInfo.toString().trim());

							for (StackTraceElement ste : thread.getStackTrace()) {
								System.err.println("\t" + ste.toString().trim());
							}
						}
					}
				}
			}
		}
	}
}
