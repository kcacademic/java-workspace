package com.sapient.learning.tempusfugit;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;

import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;
import com.sapient.learning.interleave.MyCounter;

public class MyCounterTests {

	@Rule
	public ConcurrentRule concurrently = new ConcurrentRule();
	@Rule
	public RepeatingRule rule = new RepeatingRule();

	private static MyCounter counter = new MyCounter();

	@Test
	@Concurrent(count = 2)
	@Repeating(repetition = 10)
	public void runsMultipleTimes() {
		counter.increment();
	}

	@AfterClass
	public static void annotatedTestRunsMultipleTimes() throws InterruptedException {
		System.out.println("Final Counter: " + counter.getCount());
		assertEquals(counter.getCount(), 20);
	}

}
