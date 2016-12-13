package com.experiments.experiments.readerwriter;

import java.util.concurrent.Semaphore;

/**
 * Third ReaderWriter, with fairness for both reader and writer. Reader/Writer
 * will be queued in line to get access to resource. Assumes on
 * acquiring/releasing semaphore, threads are blocked/unblocked in FIFO manner.
 * 
 * @author abbhasin
 *
 */
public abstract class FairReaderWriter {
	private Semaphore resource = new Semaphore(1);
	private Semaphore rLock = new Semaphore(1);
	private Semaphore rwServiceQueue = new Semaphore(1);
	private int rCount = 0;

	public void read() throws InterruptedException {
		/**
		 * <p>
		 * each reader takes lock on service queue, and releases it before
		 * entering critical section. Entering of critical section is managed by
		 * the resource semaphore. If writer has the resource, then readers will
		 * have to wait for writer to release it. Else if one reader has the
		 * resource, then multiple writers passing through service queue can
		 * directly enter critical section.
		 * </p>
		 * <p>
		 * If service queue lock has been taken by the writer and there are
		 * multiple readers in critical section holding the resource, then
		 * writer is waiting on resource to get released and has acquired the
		 * service queue lock. Thus all other readers will wait at the service
		 * queue. Once writer gets the resource, it releases the service queue.
		 * At that time, the next reader will try to acquire the resource since
		 * it will be the first reader after a writer and will have to wait till
		 * the writer releases the resource.
		 * </p>
		 */
		rwServiceQueue.acquire();
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
		rwServiceQueue.release();

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
		 * each writer will take lock on service queue and release it before
		 * entering the Critical section. each writer also needs to take the
		 * resource which is inside the service queue lock block. if the
		 * resource is with readers, the writer will have to wait on the
		 * resource.
		 */
		rwServiceQueue.acquire();
		{
			/**
			 * writer just try to acquire the resource. Only one writer at a
			 * time acquires the resource.
			 */
			resource.acquire();
		}
		rwServiceQueue.release();

		writeSomething();

		resource.release();

	}

	protected abstract void writeSomething();

	protected abstract void readSomething();

}
