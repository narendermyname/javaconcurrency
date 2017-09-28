/**
 * 
 */
package com.naren.cocurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import com.naren.thread.ThreadPool;

/**
 * @author ntanwa
 *
 */
public class ReceCondition {

	private static AtomicInteger counter = new AtomicInteger();
	
	private static int count = 0;

	/**
	 * 
	 * @param args
	 */
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		ExecutorService service = ThreadPool.POOL;

		Runnable increment = () -> {
			IntStream.range(0, 10000).forEach(e -> {
				counter.incrementAndGet();
				count++;
			});
		};

		Runnable decrement = () -> {
			IntStream.range(0, 10000).forEach(e -> {
				counter.decrementAndGet();
				count--;

			});
		};
		List<Future> futures =  new ArrayList<>();
		for(int i=1;i<=5;i++){
			futures.add(service.submit(increment));
		}
		for(int i=1;i<=5;i++){
			futures.add(service.submit(decrement));
		}
		
		for(Future<?> future:futures){
			try {
				future.get();
			} catch (InterruptedException | ExecutionException e) {
				System.out.println("Error : "+e.getMessage());
			}
		}
		
		System.out.println("Non Atomic Expected output = non zero : "+count);
		System.out.println("Atomic Expected output = 0 : "+counter.get());
		service.shutdown();
	}

}
