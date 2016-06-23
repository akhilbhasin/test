package com.experiments.experiments.perterson;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PetersonManager {
	
	
	public void testPeterson(){
		ExecutorService executor = Executors.newFixedThreadPool(2);
		SharedObject s = new SharedObject();
		int i=0;
		while(true){	
			MyTask t = new MyTask(i, s);
			executor.submit(t);
			i = 1-i;
		}
	}

}
