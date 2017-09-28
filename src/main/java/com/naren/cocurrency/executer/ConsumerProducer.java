/**
 * 
 */
package com.naren.cocurrency.executer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

import com.naren.thread.ThreadPool;

/**
 * @author narender
 *
 */
public class ConsumerProducer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//withBlockingQueue();
		withLock();
	}

	/**
	 * Use await with time out to avoid deadlock.
	 * Use no of thread = consumer + producer to avoid dead lock
	 */
	private static void withLock() {
		Lock lock = new ReentrantLock(true);

		Condition full = lock.newCondition();
		Condition empty = lock.newCondition();

		List<String> list = new ArrayList<>();

		ExecutorService service = ThreadPool.POOL;

		Runnable consumer = () -> {
			IntStream.range(0, 10).forEach(e -> {
				lock.lock();

				try {
					while (list.isEmpty()) {
						empty.await(); 
					}
					list.remove(""+e);
					System.out.println("Consuming "+e);
					full.signalAll();
				} catch (InterruptedException e1) {
					System.out.println("Error : " + e1.getMessage());
				} finally {
					lock.unlock();
				}
			});
		};

		Runnable producer = () -> {
			IntStream.range(0, 10).forEach(e -> {
				lock.lock();

				try {
					while (list.size() == 10) {
						full.await();
					}
					list.add(""+e);
					System.out.println("Producing "+e);
					empty.signalAll();
				} catch (InterruptedException e1) {
					System.out.println("Error : " + e1.getMessage());
				} finally {
					lock.unlock();
				}

			});
		};

		service.execute(consumer);

		service.execute(producer);

		service.shutdown();
	}

	/**
	 * 
	 */
	private static void withBlockingQueue() {
		BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
		Thread thread1 = new Thread(new Consumer(queue, "Consumer"));
		Thread thread2 = new Thread(new Producer(queue, "Producer"));
		thread2.start();
		thread1.start();
	}

}
