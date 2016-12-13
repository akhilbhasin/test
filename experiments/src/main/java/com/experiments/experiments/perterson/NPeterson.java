package com.experiments.experiments.perterson;

import java.util.concurrent.atomic.AtomicIntegerArray;

import com.google.common.base.Preconditions;

public abstract class NPeterson implements Runnable {
	private final int i; // my thread number; 
	private final int n; // number of threads
	private final AtomicIntegerArray iWantToEnterWaitingRoom; // has to be of
																// size n
	private final AtomicIntegerArray lastToEnterWaitingRoom; // there are n-1
																// waiting
																// rooms.

	protected NPeterson(final int i, final int n, final AtomicIntegerArray iWantToEnterWaitingRoom,
			final AtomicIntegerArray lastToEnterWaitingRoom) {
		this.i = i;
		this.n = n;
		Preconditions.checkArgument(iWantToEnterWaitingRoom.length() == n);
		Preconditions.checkArgument(lastToEnterWaitingRoom.length() == n - 1);
		this.iWantToEnterWaitingRoom = iWantToEnterWaitingRoom;
		this.lastToEnterWaitingRoom = lastToEnterWaitingRoom;
		init();
	}

	private void init() {
		for (int j = 0; j < n; j++) {
			iWantToEnterWaitingRoom.set(j, -1);
		}
		for (int waitingRoomNo = 0; waitingRoomNo < n - 1; waitingRoomNo++) {
			lastToEnterWaitingRoom.set(waitingRoomNo, -1);
		}
	}

	public void run() {
		// n-1 is the number of waiting room, each task enters each waiting room
		// one by one and then enters the critical section.
		for (int count = 0; count < n - 1; count++) {
			iWantToEnterWaitingRoom.set(i, count);
			lastToEnterWaitingRoom.set(count, i);

			while (lastToEnterWaitingRoom.get(count) == i && areOtherTasksInWaitingRoomGreaterThanOrEqualToMine())
				;
		}
		Thread t = new Thread();
		enterCriticalSection();

		iWantToEnterWaitingRoom.set(i, -1);
	}

	// if there are other taks in the waiting room of higher/equal number than
	// my waiting room, then wait.
	private boolean areOtherTasksInWaitingRoomGreaterThanOrEqualToMine() {
		for (int k = 0; k < n; k++) {
			if (k == i) {
				continue;
			}
			if (iWantToEnterWaitingRoom.get(k) >= iWantToEnterWaitingRoom.get(i)) {
				return true;
			}
		}
		return false;
	}

	protected abstract void enterCriticalSection();
}
