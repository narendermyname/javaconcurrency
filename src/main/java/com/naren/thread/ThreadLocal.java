/**
 * 
 */
package com.naren.thread;

import java.util.Date;

/**
 * @author ntanwa
 *
 */
public class ThreadLocal {

	// SimpleDateFormat is not thread-safe, so give one to each thread
	@SuppressWarnings("unused")
	private static final ThreadLocal formatter = new ThreadLocal();

	public String formatIt(Date date) {
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
