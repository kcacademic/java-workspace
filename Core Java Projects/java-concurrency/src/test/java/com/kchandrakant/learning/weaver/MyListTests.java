package com.kchandrakant.learning.weaver;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.testing.threadtester.AnnotatedTestRunner;
import com.google.testing.threadtester.ThreadedAfter;
import com.google.testing.threadtester.ThreadedBefore;
import com.google.testing.threadtester.ThreadedMain;
import com.google.testing.threadtester.ThreadedSecondary;

public class MyListTests {

	MyList<Object> list;

	@ThreadedBefore
	public void before() {
		list = new MyList<Object>();
	}

	@ThreadedMain
	public void mainThread() {
		list.putIfAbsent("A");
	}

	@ThreadedSecondary
	public void secondThread() {
		list.putIfAbsent("A");
	}

	@ThreadedAfter
	public void after() {
		assertEquals(1, list.size());
	}

	// This method is invoked by the regular JUnit test runner.
	@Test
	public void testThreading() {
		AnnotatedTestRunner runner = new AnnotatedTestRunner();
		// Run all Weaver tests in this class, using MyList as the Class Under Test.
		runner.runTests(this.getClass(), MyList.class);
	}

	@Test
	public void testPutIfAbsent() {
		MyList<Object> list = new MyList<Object>();
		list.putIfAbsent("A");
		list.putIfAbsent("A");
		assertEquals(1, list.size());
	}

}
