package com.kchandrakant.learning.stress;

import java.math.BigInteger;

public class AtomicBigCounter {
	private BigInteger count = BigInteger.ZERO;

	public BigInteger count() {
		return count;
	}

	public void inc() {
		count = count.add(BigInteger.ONE);
	}
}
