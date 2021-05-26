package com.kchandrakant.learning.interleave;

import java.util.concurrent.atomic.AtomicInteger;

public class MyAggregate {

	private int sum;

	private AtomicInteger safeSum = new AtomicInteger();

	public void add(int start, int end) {
		for (int i = start; i <= end; i++) {
			sum += i;
		}
	}

	public void performSafeSummation(int start, int end) {
		synchronized (this) {
			for (int i = start; i <= end; i++)
				sum += i;
		}
	}

	public void performAtomicSummation(int start, int end) {
		for (int i = start; i <= end; i++)
			safeSum.getAndAdd(i);
	}

	public int getSum() {
		return sum;
	}

	public int getSafeSum() {
		return safeSum.get();
	}

}
