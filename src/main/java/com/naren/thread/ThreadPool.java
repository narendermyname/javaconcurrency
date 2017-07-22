package com.naren.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Custom ThreadPool
 * @author ntanwa
 *
 */
public class ThreadPool{
	public static final ThreadPoolExecutor POOL = new ThreadPoolExecutor(4, 4, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
}
