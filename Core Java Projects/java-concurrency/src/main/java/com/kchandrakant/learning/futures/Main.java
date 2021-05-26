package com.kchandrakant.learning.futures;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Main {

	public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {

		// Using isDone() and get() to Obtain Results

		Future<Integer> future = new Calculator().calculate(10);
		while (!future.isDone()) {
			System.out.println("Calculating...");
			Thread.sleep(300);
		}
		Integer result = future.get();
		System.out.println(result);

		Future<Integer> anotherFuture = new Calculator().calculate(10);
		Integer anotherResult = anotherFuture.get(1000, TimeUnit.MILLISECONDS);
		System.out.println(anotherResult);

		// Canceling a Future With cancel()

		Future<Integer> yetAnotherFuture = new Calculator().calculate(4);
		boolean canceled = yetAnotherFuture.cancel(true);
		System.out.println(canceled);
		System.out.println(yetAnotherFuture.isCancelled());

	}

}
