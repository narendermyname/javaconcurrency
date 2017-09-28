/**
 * 
 */
package com.naren.cocurrency.latch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import com.naren.thread.ThreadPool;

/**
 * @author ntanwa
 *
 */
public class CountDownLatch {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		java.util.concurrent.CountDownLatch  latch =  new java.util.concurrent.CountDownLatch(3);
		
		ExecutorService service =  ThreadPool.POOL;
		
		CompletionService<String> compSerice = new ExecutorCompletionService<>(service);
		
		
		IntStream.range(1, 4).forEach(e -> {
			compSerice.submit(()->{
				System.out.println("Starting service "+e);
				TimeUnit.SECONDS.sleep(2);
				latch.countDown();
				return "Service "+e;
			});
		});
		
		IntStream.range(1, 4).forEach(e -> {
			try {
				System.out.println(compSerice.take().get());
			} catch (InterruptedException e1) {
				System.out.println("Error : "+e1.getMessage());
			} catch (ExecutionException e1) {
				System.out.println("Error : "+e1.getMessage());
			}
		});
		try {
			latch.await();
		} catch (InterruptedException e1) {
			System.out.println("Error : "+e1.getMessage());
		}
		
		System.out.println("Start receiving requestions");
	}

}
