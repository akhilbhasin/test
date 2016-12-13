package com.experiments.experiments.perterson;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MyTask extends Perterson {
	private final SharedObject s;

	public MyTask(int i, final SharedObject s) {
		super(i);
		this.s = s;
	}

	@Override
	protected void enterCriticalSection() {
		try {
			System.out.println("Thread No.:" + getI() + " ,val of s:" + s.get());
			
			int x = getI();

			Thread.sleep(100*randInt2());

			System.out.println("Thread No.:" + getI() + " ,val of x:" + x);
			
			s.set(x);
			
			System.out.println("Thread No.:" + getI() + " ,val of s:" + s.get());
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

//	public static int randInt() {
//		return ThreadLocalRandom.current().nextInt(10);
//	}
	
	public static int randInt2(){
		return new Random().nextInt(2);
	}

}
