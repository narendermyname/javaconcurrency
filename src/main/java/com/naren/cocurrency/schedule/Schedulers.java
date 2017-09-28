/**
 * 
 */
package com.naren.cocurrency.schedule;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author ntanwa
 *
 */
public class Schedulers {

	/**
	 * 
	 */
	public Schedulers() {
	}

	/**
	 * @param args
	 *  Creates and executes a periodic action that becomes enabled first after the given initial delay, and 
	 *  subsequently with the given period; that is executions will commence after initialDelay then
	 *  initialDelay+period, then initialDelay + 2 * period, and so on. If any execution of 
	 *  the task encounters an exception, subsequent executions are suppressed. Otherwise, 
	 *  the task will only terminate via cancellation or termination of the executor. 
	 *  If any execution of this task takes longer than its period, then subsequent 
	 *  executions may start late, but will not concurrently execute.
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {

		Timer timer =  new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("Task executing");
			}
		}, 2);
		
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("Task executing with a period");
			}
		}, 2, 1);
		
		
		//SwithScheduleExecutorService();

	}

	/**
	 * @throws InterruptedException
	 */
	private static void withScheduleExecutorService() throws InterruptedException {
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);
		Runnable fixedRateTask = () ->{
			System.out.println(System.currentTimeMillis()/1000);
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				System.out.println("Error : "+e.getMessage());
			}
		};
		scheduler.scheduleAtFixedRate(fixedRateTask, 0, 4, TimeUnit.SECONDS);
		
		scheduler.scheduleWithFixedDelay(fixedRateTask, 0, 1, TimeUnit.SECONDS);
		
		TimeUnit.SECONDS.sleep(10);
		scheduler.shutdown();
		scheduler.awaitTermination(10, TimeUnit.SECONDS);
	}

}
