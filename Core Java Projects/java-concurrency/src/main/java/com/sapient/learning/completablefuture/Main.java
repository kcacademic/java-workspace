package com.sapient.learning.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws InterruptedException, ExecutionException {

		// Using CompletableFuture as a Simple Future

		CompletableFuture<String> completableFuture = new CompletableFuture<>();
		Executors.newCachedThreadPool().submit(() -> {
			Thread.sleep(500);
			completableFuture.complete("Hello");
			return null;
		});
		Future<String> future = completableFuture;
		String result = future.get();
		System.out.println(result);

		Future<String> completedFuture = CompletableFuture.completedFuture("Hello");
		String anootherResult = completedFuture.get();
		System.out.println(anootherResult);

		// CompletableFuture with Encapsulated Computation Logic

		CompletableFuture<String> anotherCompletableFuture = CompletableFuture.supplyAsync(() -> "Hello");
		System.out.println(anotherCompletableFuture.get());

		// Processing Results of Asynchronous Computations

		CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> "Hello");
		CompletableFuture<String> completableFuture2 = completableFuture1.thenApply(s -> s + " World");
		System.out.println(completableFuture2.get());

		CompletableFuture<String> completableFuture3 = CompletableFuture.supplyAsync(() -> "Hello");
		CompletableFuture<Void> completableFuture4 = completableFuture3
				.thenAccept(s -> System.out.println("Computation returned: " + s));
		completableFuture4.get();

		CompletableFuture<String> completableFuture5 = CompletableFuture.supplyAsync(() -> "Hello");
		CompletableFuture<Void> completableFuture6 = completableFuture5
				.thenRun(() -> System.out.println("Computation finished."));
		completableFuture6.get();

		// Combining Futures

		CompletableFuture<String> completableFuture7 = CompletableFuture.supplyAsync(() -> "Hello")
				.thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " World"));
		System.out.println(completableFuture7.get());

		CompletableFuture<String> completableFuture8 = CompletableFuture.supplyAsync(() -> "Hello")
				.thenApply(s -> s + " World");
		System.out.println(completableFuture8.get());

		CompletableFuture<String> completableFuture9 = CompletableFuture.supplyAsync(() -> "Hello")
				.thenCombine(CompletableFuture.supplyAsync(() -> " World"), (s1, s2) -> s1 + s2);
		System.out.println(completableFuture9.get());

		CompletableFuture.supplyAsync(() -> "Hello").thenAcceptBoth(CompletableFuture.supplyAsync(() -> " World"),
				(s1, s2) -> System.out.println(s1 + s2));

		// Running Multiple Futures in Parallel

		CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "Hello");
		CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "Beautiful");
		CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> "World");
		CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(future1, future2, future3);
		combinedFuture.get();
		String combined = Stream.of(future1, future2, future3).map(CompletableFuture::join)
				.collect(Collectors.joining(" "));
		System.out.println(combined);

		// Handling Errors

		String name = null;
		CompletableFuture<String> completableFuture10 = CompletableFuture.supplyAsync(() -> {
			if (name == null) {
				throw new RuntimeException("Computation error!");
			}
			return "Hello, " + name;
		}).handle((s, t) -> s != null ? s : "Hello, Stranger!");
		System.out.println(completableFuture10.get());

		CompletableFuture<String> completableFuture11 = new CompletableFuture<>();
		completableFuture11.completeExceptionally(new RuntimeException("Calculation failed!"));
		//completableFuture11.get();

		// Async Methods

		CompletableFuture<String> completableFuture12 = CompletableFuture.supplyAsync(() -> "Hello");
		CompletableFuture<String> future12 = completableFuture12.thenApplyAsync(s -> s + " World");
		System.out.println(future12.get());

	}

}
