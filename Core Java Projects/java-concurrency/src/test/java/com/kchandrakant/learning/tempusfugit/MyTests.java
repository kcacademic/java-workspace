package com.kchandrakant.learning.tempusfugit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;

import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;

public class MyTests {

	@Rule
	public ConcurrentRule concurrently = new ConcurrentRule();
	@Rule
	public RepeatingRule rule = new RepeatingRule();

	private static int counter = 0;

	private static final AtomicInteger safeCounter = new AtomicInteger();

	@Test
	@Concurrent(count = 20)
	@Repeating(repetition = 100)
	public void runsMultipleTimes() {
		counter++;
		safeCounter.getAndIncrement();
	}

	@AfterClass
	public static void annotatedTestRunsMultipleTimes() throws InterruptedException {
		System.out.println("Final Counter: " + counter + " , Safe Counter: " + safeCounter.get());
		assertThat(counter, not(2000));
		assertThat(safeCounter.get(), equalTo(2000));
	}

}
