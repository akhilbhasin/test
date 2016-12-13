package com.experiments.experiments.readerwriter;

import java.util.concurrent.Semaphore;

/**
 * preference given to reader. If one reader has acquired the resouce, then more
 * readers can read from the same resource. Writers will wait till all readers
 * have completed reading.
 * 
 * @author abbhasin
 *
 */
public abstract class FirstReaderWriter {
	Semaphore resource = new Semaphore(1); // there is on resource, ex a file,
											// for reading and writing.
	Semaphore rLock = new Semaphore(1); // using semaphore as mutex
	private int rCount = 0; // read count

	public void read() throws InterruptedException {
		/**
		 * if one reader has acquired the resource, following readers don't need
		 * to acquire the resource, they make use of the same resource and are
		 * not blocked.
		 */

		rLock.acquire();
		rCount++;
		if (rCount == 1) {
			resource.acquire();
		}
		rLock.release();

		readSomething();

		/**
		 * the last reader releases the resource for the writer or next reader
		 * to acquire. changing the value of the rCount is guarded by a read
		 * mutex.
		 */
		rLock.acquire();
		rCount--;
		if (rCount == 0) {
			resource.release();
		}
		rLock.release();

	}

	public void write() throws InterruptedException {
		/**
		 * writer just try to acquire the resource. Only one writer at a time
		 * acquires the resource.
		 */
		resource.acquire();

		writeSomething();

		resource.release();

	}

	protected abstract void writeSomething();

	protected abstract void readSomething();

}
