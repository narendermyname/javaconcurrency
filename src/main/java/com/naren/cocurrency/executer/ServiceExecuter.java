/**
 * 
 */
package com.naren.cocurrency.executer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

/**
 * Thread executer
 * @author narender
 *
 */
public class ServiceExecuter {

	public int count=0;
	private static final Logger LOG=Logger.getLogger(ServiceExecuter.class);
	/**
	 * 
	 */
	public ServiceExecuter() {
	}

	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws ExecutionException 
	 */
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		//final ReentrantLock rl = new ReentrantLock();
		/*Thread t1=new Thread(new Runnable() {
			public synchronized void run() {
				//rl.lock();
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}finally
				{
					//rl.unlock();
				}
				System.out.println(Thread.currentThread().getName());
			}
		});
		t1.join();
		t1.start();
		Thread t2=new Thread(new Runnable() {
			public void run() {
				//	rl.lock();
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}finally
				{
					//	rl.unlock();
				}
				System.out.println(Thread.currentThread().getName());
			}
		});
		t2.join();t2.start();

		Thread t3=new Thread(new Runnable() {
			public void run() {
				//rl.lock();
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}finally
				{
					//rl.unlock();
				}
				System.out.println(Thread.currentThread().getName());
			}
		});


		t3.start();


		t3.join();*/
		System.out.println("Thread :"+Thread.currentThread().getName());
		//new ServiceExecuter().executer();
		ExecutorService executorService = Executors.newSingleThreadExecutor();

		Vector<Callable<String>> callables = new Vector<Callable<String>>();

		callables.add(new Callable<String>() {
			public String call() throws Exception {
				//Thread.sleep(1000);
				return "Task 1";

			}
		});
		callables.add(new Callable<String>() {
			public String call() throws Exception {
				//Thread.sleep(1000);
				return "Task 2";
			}
		});
		callables.add(new Callable<String>() {
			public String call() throws Exception {
				//Thread.sleep(1000);
				return "Task 3";
			}
		});

		List<Future<String>> futures = executorService.invokeAll(callables);

		for(Future<String> future : futures){
			System.out.println("future.get = " + future.get());
		}
		Future future = executorService.submit(new Callable<String>() {
			public String call() throws Exception {
				Thread.sleep(1000);
				return "Task 3444";
			}
		});
		System.out.println(future.get());
		executorService.shutdown();
		LOG.debug("END main()");
		new ServiceExecuter().executer();
	}

	public void executer(){
		int processors = Runtime.getRuntime().availableProcessors();
		LOG.debug("Available Processors : "+processors+" count : "+count);
		ExecutorService executorService = Executors.newFixedThreadPool(processors);

//		executorService.execute(new ThreadFind("Handler1",this));
//		executorService.execute(new ThreadFind("Handler2",this));
//		executorService.execute(new ThreadFind("Handler3",this));
		final ReentrantLock rl = new ReentrantLock();
		executorService.submit(new Runnable() {
			public void run() {
				rl.lock();
				try {
					Thread.sleep(1000);
					System.out.println(Thread.currentThread().getName());
				} catch (Exception e) {
					e.printStackTrace();
				}finally
				{
					rl.unlock();
				}
				
			}
		});
		executorService.submit(new Runnable() {
			public void run() {

				rl.lock();
				try {

					Thread.sleep(1000);
					System.out.println(Thread.currentThread().getName());
				} catch (Exception e) {
					e.printStackTrace();
				}
				finally
				{
					rl.unlock();
				}
				
			}
		});
		executorService.submit(new Runnable() {
			public void run() {
				rl.lock();
				try {
					Thread.sleep(1000);
					System.out.println(Thread.currentThread().getName());
				} catch (Exception e) {
					e.printStackTrace();
				}finally
				{
					rl.unlock();
				}
				

			}
		});
		LOG.debug("Count "+count);
		executorService.shutdown();

		LOG.debug("END execute()"+Thread.currentThread().getName());
	}

	private synchronized void hello(String hello){
		LOG.debug(hello);
	}

}
/**
 * Thread Handler
 * @author narender
 *
 */
class ThreadFind extends Thread{
	private static final Logger LOG=Logger.getLogger(ThreadFind.class);
	String name;
	ServiceExecuter executer;
	public ThreadFind(String name,ServiceExecuter executer){
		this.name=name;
		this.executer=executer;
	}
	@Override
	public synchronized void run(){
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		LOG.debug("THreadHandler.run() Thread : "+name);
		executer.count+=1;
	}
}