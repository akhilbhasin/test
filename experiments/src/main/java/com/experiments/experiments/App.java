package com.experiments.experiments;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.experiments.experiments.perterson.PetersonManager;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		System.out.println("Hello World!");
		Thread t1 = new Thread(() -> func());
		Thread t2 = new Thread(() -> func());
		
		System.out.println("about to run thread 1");
		t1.start();
		System.out.println("zzzzz thread 1 running");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		System.out.println("zzzzzz thread 2 about to call");
		t2.start();
		System.out.println("zzzzzz thread 2 running");
	}

	private static Lock l = new ReentrantLock();
	private static Semaphore s = new Semaphore(0);
	private static Object o = new Object();

	private static void func() {
		System.out.println("anout to take lock, threadNo.: " + Thread.currentThread().getId());
//		synchronized (o) {
//			try {				
//				System.out.println("lock taken, about to take semaphore, threadNo.: " + Thread.currentThread().getId());
////				 s.acquire();
//				o.wait();
//				
//				 System.out.println("about to release synchronised lock, threadNo.: " + Thread.currentThread().getId());
//			} catch (InterruptedException e) {
//				Thread.currentThread().interrupt();
//			}
//		}
		l.lock();
		try {				
			System.out.println("lock taken, about to take semaphore, threadNo.: " + Thread.currentThread().getId());
			 s.acquire();
			
			
			 System.out.println("about to release synchronised lock, threadNo.: " + Thread.currentThread().getId());
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		l.unlock();
		System.out.println("lock released, threadNo.: " + Thread.currentThread().getId());
	}
}
