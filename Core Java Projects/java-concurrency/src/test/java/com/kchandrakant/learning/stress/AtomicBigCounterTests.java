package com.kchandrakant.learning.stress;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigInteger;

import org.junit.Test;

public class AtomicBigCounterTests {

	@Test
	public void canIncreaseCounter() {

	}

	@SuppressWarnings("deprecation")
	@Test
	public void canIncrementCounterFromMultipleThreadsSimultaneously() throws InterruptedException {

		AtomicBigCounter counter = new AtomicBigCounter();
		
		MultithreadedStressTester stressTester = new MultithreadedStressTester();
		stressTester.stress(new Runnable() {
			public void run() {
				counter.inc();
			}
		});
		stressTester.shutdown();
		
		System.out.println("OUTPUT: " + counter.count());
		
		assertThat("final count", counter.count(), equalTo(BigInteger.valueOf(stressTester.totalActionCount())));
	}

}
