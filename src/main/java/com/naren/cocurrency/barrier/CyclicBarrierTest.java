/**
 * 
 */
package com.naren.cocurrency.barrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author ntanwa
 *
 */
public class CyclicBarrierTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		CyclicBarrier barrier1 = new CyclicBarrier(2, () -> {
			System.out.println("BarrierAction 1 executed ");
		});

		final CyclicBarrier barrier2 = new CyclicBarrier(2, () -> {
			System.out.println("BarrierAction 2 executed ");
		});

		new Thread(() -> {
			try {
				Thread.sleep(1000);
				System.out.println(Thread.currentThread().getName() + " waiting at barrier 1");
				barrier1.await();

				Thread.sleep(1000);
				System.out.println(Thread.currentThread().getName() + " waiting at barrier 2");
				barrier2.await();

				System.out.println(Thread.currentThread().getName() + " done!");

			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
		}).start();

		new Thread(() -> {
			try {
				Thread.sleep(1000);
				System.out.println(Thread.currentThread().getName() + " waiting at barrier 1");
				barrier1.await();

				Thread.sleep(1000);
				System.out.println(Thread.currentThread().getName() + " waiting at barrier 2");
				barrier2.await();

				System.out.println(Thread.currentThread().getName() + " done!");

			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
		}).start();

	}
}