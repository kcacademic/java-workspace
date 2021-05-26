package com.kchandrakant.learning.stress;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MultithreadedStressTester {

	private int threadCount = 10;

	private int iterationCount = 500;

	private Executor executor = Executors.newFixedThreadPool(10);

	public void stress(final Runnable action) throws InterruptedException {
		spawnThreads(action).await();
	}
	
	public int totalActionCount() {
		return iterationCount*threadCount;
	}
	
	public void shutdown() {
		
	}

	private CountDownLatch spawnThreads(final Runnable action) {
		final CountDownLatch finished = new CountDownLatch(threadCount);
		for (int i = 0; i < threadCount; i++) {
			executor.execute(new Runnable() {
				public void run() {
					try {
						repeat(action);
					} finally {
						finished.countDown();
					}
				}
			});
		}
		return finished;
	}

	private void repeat(Runnable action) {
		for (int i = 0; i < iterationCount; i++) {
			action.run();
		}
	}

}
