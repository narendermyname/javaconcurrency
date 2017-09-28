/**
 * 
 */
package com.naren.completable.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author ntanwa
 *
 */
public class Completable {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Completable completable =  new Completable();
		//completionService();
		
		//runAsysnc();
		completable.suplyConsume();
		
		completable.suplyConsumerAsync(); 
		System.out.println("Existing "+Thread.currentThread().getName());
		
	}

	/**
	 * Uses ForkJoinPoo.commonPool();
	 */
	private  void suplyConsumerAsync() {
		/*CompletableFuture<String> receiver = CompletableFuture.supplyAsync();

		receiver.thenApplyAsync(this::sendMsg);
		receiver.thenApplyAsync(this::sendMsg);*/
	}

	/**
	 * 
	 */
	private void suplyConsume() {
		CompletableFuture.supplyAsync(this::sendMsg).exceptionally(e -> "Error while sending message").thenAccept(this::notify_);
	}

	/**
	 * 
	 */
	private static void runAsysnc() {
		CompletableFuture.runAsync(()->{
			System.out.println("Running asysnc");
		}).thenRun(()->{
			System.out.println("then Running action");
		});
	}

	/**
	 * 
	 */
	public static void completionService() {
		ExecutorService service = Executors.newFixedThreadPool(10);
		CompletionService<String> completableService = new ExecutorCompletionService<String>(service);
		long startTime = System.currentTimeMillis();

		System.out.println("Posting message");
		IntStream.range(0, 10).forEach(index -> {
			completableService.submit(() -> {
				TimeUnit.SECONDS.sleep(2);
				return "" + index;
			});
		});
		
		completableService.submit(()->{
			System.out.println("No return..");
		},"Test message ");

		System.out.println("Start reading message");
		IntStream.range(0, 10).forEach(index -> {
			try {
				String message = completableService.take().get();
				System.out.println("Message :" + message);
			} catch (InterruptedException e) {
				System.out.println("Error : " + e.getMessage());
			} catch (ExecutionException e) {
				System.out.println("Error : " + e.getMessage());
			}
		});

		System.out.println("Time taken in process : " + (System.currentTimeMillis() - startTime));

		System.out.println("Shutting down executor.... ");
		service.shutdown();
	}
	
	private String sendMsg(){
		if("".isEmpty()){
			throw new RuntimeException();
		}
		return "message from naren";
	}
	
	private void notify_(String message){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println("Error : "+e.getMessage());
		}
		System.out.println(message+" msg send to user");
	}
}
