package com.sapient.learning.futures;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Calculator {

	private ExecutorService executor = Executors.newSingleThreadExecutor();

	public Future<Integer> calculate(Integer input) {
		return executor.submit(() -> {
			Thread.sleep(1000);
			return input * input;
		});
	}
}
