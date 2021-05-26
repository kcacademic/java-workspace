package com.kchandrakant.learning.interleave;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

public class MyAggregateTests {

	public static int performSequentialSummation(int start, int end) {
		MyAggregate sum = new MyAggregate();
		int i = start;
		while (i < end) {
			int j = i + 9 > end ? end : i + 9;
			sum.add(i, j);
			i = j + 1;
		}
		System.out.println("Sequential Sum: " + sum.getSum());
		return sum.getSum();
	}

	public static int performConcurrentSummation(int start, int end) throws InterruptedException, ExecutionException {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		MyAggregate sum = new MyAggregate();
		int i = start;
		List<Future<?>> futures = new ArrayList<Future<?>>();
		while (i < end) {
			int j = i + 9 > end ? end : i + 9;
			final int m = i;
			final int n = j;
			Future<?> f = executor.submit(() -> {
				sum.add(m, n);
			});
			futures.add(f);
			i = j + 1;
		}
		for (Future<?> future : futures)
			future.get();
		System.out.println("Unsafe Concurrent Sum: " + sum.getSum());
		return sum.getSum();
	}

	public static int performSafeConcurrentSummation(int start, int end)
			throws InterruptedException, ExecutionException {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		MyAggregate sum = new MyAggregate();
		int i = start;
		List<Future<?>> futures = new ArrayList<Future<?>>();
		while (i < end) {
			int j = i + 9 > end ? end : i + 9;
			final int m = i;
			final int n = j;
			Future<?> f = executor.submit(() -> {
				sum.performSafeSummation(m, n);
			});
			futures.add(f);
			i = j + 1;
		}
		for (Future<?> future : futures)
			future.get();
		System.out.println("Safe Concurrent Sum: " + sum.getSum());
		return sum.getSum();
	}

	public static int performAtomicConcurrentSummation(int start, int end)
			throws InterruptedException, ExecutionException {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		MyAggregate sum = new MyAggregate();
		int i = start;
		List<Future<?>> futures = new ArrayList<Future<?>>();
		while (i < end) {
			int j = i + 9 > end ? end : i + 9;
			final int m = i;
			final int n = j;
			Future<?> f = executor.submit(() -> {
				sum.performAtomicSummation(m, n);
			});
			futures.add(f);
			i = j + 1;
		}
		for (Future<?> future : futures)
			future.get();
		System.out.println("Atomic Concurrent Sum: " + sum.getSafeSum());
		return sum.getSafeSum();
	}

	@Test
	public void testSequentialSummation() {
		assertEquals(50005000, performSequentialSummation(1, 10000));
	}

	@Test
	public void testConcurrentSummation() throws InterruptedException, ExecutionException {
		assertNotEquals(50005000, performConcurrentSummation(1, 10000));
	}

	@Test
	public void testSafeConcurrentSummation() throws InterruptedException, ExecutionException {
		assertEquals(50005000, performSafeConcurrentSummation(1, 10000));
	}

	@Test
	public void testAtomicConcurrentSummation() throws InterruptedException, ExecutionException {
		assertEquals(50005000, performAtomicConcurrentSummation(1, 10000));
	}

}
