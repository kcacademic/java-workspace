package com.kchandrakant.learning.weaver;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.testing.threadtester.AnnotatedTestRunner;
import com.google.testing.threadtester.ThreadedAfter;
import com.google.testing.threadtester.ThreadedBefore;
import com.google.testing.threadtester.ThreadedMain;
import com.google.testing.threadtester.ThreadedSecondary;
import com.kchandrakant.learning.interleave.MyCounter;

public class MyCounterTests {

	private MyCounter counter;

	@ThreadedBefore
	public void before() {
		counter = new MyCounter();
	}

	@ThreadedMain
	public void mainThread() {
		counter.increment();
	}

	@ThreadedSecondary
	public void secondThread() {
		counter.increment();
	}

	@ThreadedAfter
	public void after() {
		assertEquals(2, counter.getCount());
	}

	// This method is invoked by the regular JUnit test runner.
	@Test
	public void testCounter() {
		// Run all Weaver tests in this class, using MyList as the Class Under Test.
		new AnnotatedTestRunner().runTests(this.getClass(), MyCounter.class);
	}

}
