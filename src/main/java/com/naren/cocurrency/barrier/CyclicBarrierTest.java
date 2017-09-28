/**
 * 
 */
package com.naren.cocurrency.barrier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.IntStream;

/**
 * @author ntanwa
 *
 */
public class CyclicBarrierTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//withThread();
		withExecutor();

	}

	/**
	 * 
	 */
	private static void withExecutor() {
		
		// Note : -  use no of thread = parties to avoid dead lock 
		
		ExecutorService service = Executors.newFixedThreadPool(2);
		
		CyclicBarrier barrier = new CyclicBarrier(2, () -> {
			System.out.println("BarrierAction 1 executed ");
		});
		
		List<Future<String>> futures = new ArrayList();
		
		IntStream.range(1, 3).forEach(e -> {
			futures.add(service.submit(()->{
				if(e == 2) throw new Exception("Invalid request");
				System.out.println("Task "+e+" waiting.");
				barrier.await(2, TimeUnit.SECONDS); //1 set await with timeout option
				
				return "Task "+e;
			}));
		});
		
		futures.forEach(e ->{
			try {
				System.out.println("Getting Task task : "+e.get(2, TimeUnit.SECONDS));
				/*e.cancel(true);*/
			} catch (InterruptedException | ExecutionException | TimeoutException e1) {
				System.out.println("Error InterruptedException : "+e1.getMessage());
			}finally {
				//e.cancel(true); //2 Way to cancel task will avoid dead lock
			}
		});
		service.shutdown();
		try {
			service.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException e1) {
			System.out.println("Error : "+e1.getMessage());
		}
	}

	/**
	 * 
	 */
	private static void withThread() {
		CyclicBarrier barrier1 = new CyclicBarrier(2, () -> {
			System.out.println("BarrierAction 1 executed ");
		});

		final CyclicBarrier barrier2 = new CyclicBarrier(2, () -> {
			System.out.println("BarrierAction 2 executed ");
		});

		new Thread(() -> {
			try {
				Thread.sleep(1000);
				System.out.println(10/0);
				System.out.println(Thread.currentThread().getName() + " waiting at barrier 1");
				try {
					barrier1.await(1, TimeUnit.SECONDS);
				} catch (TimeoutException e) {
					System.out.println("Error : "+e.getMessage());
				}

				Thread.sleep(1000);
				System.out.println(Thread.currentThread().getName() + " waiting at barrier 2");
				barrier2.await(1, TimeUnit.SECONDS);

				System.out.println(Thread.currentThread().getName() + " done!");

			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				System.out.println("Error : "+e.getMessage());
			}
		}).start();

		new Thread(() -> {
			try {
				Thread.sleep(1000);
				System.out.println(Thread.currentThread().getName() + " waiting at barrier 1");
				try {
					barrier1.await(1, TimeUnit.SECONDS);
				} catch (TimeoutException e) {
					System.out.println("Error : "+e.getMessage());
				}

				Thread.sleep(1000);
				System.out.println(Thread.currentThread().getName() + " waiting at barrier 2");
				try {
					barrier2.await(1, TimeUnit.SECONDS);
				} catch (TimeoutException e) {
					System.out.println("Error : "+e.getMessage());
				}

				System.out.println(Thread.currentThread().getName() + " done!");

			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
		}).start();
	}
}