package com.experiments.experiments.readerwriter;

import java.util.concurrent.Semaphore;

/**
 * <p>
 * gives preference to the writer. If readers have the resource, and a writer
 * wants to write, then readers which are not in critical section gets blocked.
 * After some time allr readers which were reading complete and release the
 * readerWriter lock.
 * </p>
 * <p>
 * Writers take up the resource/lock. As long as there is one writer writing,
 * more writers keep coming. the lock is released by the last writer writing.
 * </p>
 * 
 * @author abbhasin
 *
 */
public abstract class SecondReaderWriter {

	private int rCount = 0, wCount = 0;
	private Semaphore resource = new Semaphore(1); // resource semaphore
	private Semaphore rLock = new Semaphore(1); // mutex for readers
	private Semaphore wLock = new Semaphore(1);// mutex for writers
	private Semaphore rwLock = new Semaphore(1); // semaphore to tell if writer
													// wants access.

	public void read() throws InterruptedException {

		/**
		 * each reader acquires and releases the RWLock before entering the
		 * critical section. If a writer wants the access to a resource, rwLock
		 * gets acquired by the writer, and no more reader can take the RWLock
		 * until all writers have written.
		 */
		rwLock.acquire();
		{
			/**
			 * if one reader has acquired the resource, following readers don't
			 * need to acquire the resource, they make use of the same resource
			 * and are not blocked.
			 */
			rLock.acquire();
			{
				rCount++;
				if (rCount == 1) {
					resource.acquire();
				}
			}
			rLock.release();
		}
		rwLock.release();

		readSomething();

		/**
		 * the last reader releases the resource for the writer or next reader
		 * to acquire. changing the value of the rCount is guarded by a read
		 * mutex.
		 */
		rLock.acquire();
		{
			rCount--;
			if (rCount == 0) {
				resource.release();
			}
		}
		rLock.release();

	}

	public void write() throws InterruptedException {

		/**
		 * guarding access to wCount.
		 */
		wLock.acquire();
		{
			wCount++;
			/**
			 * first writer acquires the RWLock.
			 */
			if (wCount == 1) {
				rwLock.acquire();
			}
		}
		wLock.release();

		resource.acquire();
		{

			writeSomething();

		}
		resource.release();

		wLock.acquire();
		{
			wCount--;
			/**
			 * last writer releases the RWLock.
			 */
			if (wCount == 0) {
				rwLock.release();
			}
		}
		wLock.release();

	}

	protected abstract void writeSomething();

	protected abstract void readSomething();

}
