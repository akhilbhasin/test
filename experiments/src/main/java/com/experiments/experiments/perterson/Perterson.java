package com.experiments.experiments.perterson;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

import com.google.common.base.Preconditions;

/**
 * works only for 2 threads.
 * <p>
 * takes i which is the thread no. which can be only 0 or 1.
 * </p>
 * This might not work since we need the variable assignment to be atomic
 * 
 * @author abbhasin
 *
 */
public abstract class Perterson implements Runnable {
	
	private final int i; // my thread number; can be only 1 or 0
	/**
	 * 0 means false, I do not want to enter, 1 means I want to enter. Whenever
	 * thread want to enter it sets the value of its index 'i' to 1; and
	 * whenever it had done its work it sets the value to 0.
	 */
	private volatile static AtomicIntegerArray doIWantToEnter = new AtomicIntegerArray(2);
	/**
	 * There is only 1 waitingRoom since there can be at max 2 threads competing
	 * with Peterson.
	 * <p>
	 * 1 thread waits in the waiting room while the other enters its critical
	 * section. waitingRoom value will be assigned to the thread number who
	 * wishes to enter its critical section ,i.e. it can be either 0 or 1.
	 * section.
	 * </p>
	 */
	private volatile static AtomicInteger lastToWaitingRoom = new AtomicInteger(-1);
	static {
		for (int q = 0; q < 2; q++) {
			doIWantToEnter.set(q, 0);
		}	
		
	}

	protected Perterson(final int i) {
		Preconditions.checkArgument(i == 0 || i == 1);
		this.i = i;
	}

	public void run() {
		doIWantToEnter.set(i, 1);	// I want to enter
		lastToWaitingRoom.set(i); // I enter the waiting room
		/**
		 * if the other thread wants to enter its criticalSection and i came
		 * last in the waitingRoom, then i have to wait.
		 */
		while (doIWantToEnter.get(1-i) == 1 && lastToWaitingRoom.get() == i) {
			
		}
		System.out.println("Thread No.:" + getI() + "doIWantToEnter:" + doIWantToEnter.get(0) + " ;" + doIWantToEnter.get(1)
				+ "  ,waitingRoom:" + lastToWaitingRoom);
		enterCriticalSection();

		doIWantToEnter.set(i, 0); // I do not want to enter anymore
	}

	protected abstract void enterCriticalSection();

	protected int getI() {
		return this.i;
	}

}
