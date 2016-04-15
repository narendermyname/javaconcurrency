/**
 * 
 */
package com.naren.cocurrency.executer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author narender
 *
 */
public class GetDomain {

	/**
	 * 
	 */
	public GetDomain() {
	}

	/**
	 * @param args
	 */
	@Test
	public void getDomain() {
		String email="nstanwar@jamcracker.com";
		String domain=email.substring(email.indexOf('@')+1, email.indexOf('.'));
		assertEquals(domain, "jamcradcker");
		//System.out.println(domain);

	}

}
