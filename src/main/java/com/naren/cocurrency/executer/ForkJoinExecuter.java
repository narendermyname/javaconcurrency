/**
 * 
 */
package com.naren.cocurrency.executer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import org.apache.log4j.Logger;

/**
 * @author narender
 *
 */
public class ForkJoinExecuter {

	private static final Logger LOG=Logger.getLogger(ForkJoinExecuter.class);

	/**
	 * 
	 */
	public ForkJoinExecuter() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LOG.debug("Start Main");
		new ForkJoinExecuter().execute();
		LOG.debug("End Main");
	}
	
	public void execute(){
		LOG.debug("Start Execute");
		int maxThreads=Runtime.getRuntime().availableProcessors();
		ForkJoinPool pool=new ForkJoinPool(maxThreads);
		pool.execute(new ForkJoinAction("bye"));
		pool.execute(new ForkJoinAction("help"));
		pool.execute(new ForkJoinAction("hello"));
		pool.shutdown();
		LOG.debug("End Execute");
		
	}
	
	public void sayHello(){
		for(int i=0;i<3;i++){
			LOG.debug("Hello From "+i);
		}
	}
	public void sayBye(){
		for(int i=0;i<3;i++){
			LOG.debug("Bye From "+i);
		}
	}
}

class ForkJoinAction extends RecursiveAction{

	/**
	 * 
	 */
	private static final Logger LOG=Logger.getLogger(ForkJoinExecuter.class);

	private static final long serialVersionUID = -883170309205574238L;
	private String message;
	
	public ForkJoinAction(String message){
		this.message=message;
	}
	@Override
	protected void compute() {
		LOG.debug("Start ForkJoinAction.compute");
		run1();
		LOG.debug("End ForkJoinAction.compute");
	}
	
	private void run1(){
		for(int i=0;i<100;i++)
			LOG.debug(message+i);
	}
}
