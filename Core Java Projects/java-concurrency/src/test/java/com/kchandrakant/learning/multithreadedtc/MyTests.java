package com.kchandrakant.learning.multithreadedtc;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.TestFramework;

public class MyTests extends MultithreadedTestCase {

	private static int counter;

	@Override
	public void initialize() {
		counter = 0;
	}

	public void thread1() throws InterruptedException {
		counter++;
	}

	public void thread2() throws InterruptedException {
		counter++;
	}

	@Override
	public void finish() {
		assertThat(counter, equalTo(2));
	}

	@Test
	public void testMTCBoundedBuffer() throws Throwable {
		TestFramework.runManyTimes(new MyTests(), 10000);
	}
}
