package com.sapient.learning;

import java.util.Arrays;
import java.util.concurrent.SubmissionPublisher;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		// Create Publisher
		SubmissionPublisher<String> publisher = new SubmissionPublisher<>();

		// Register Subscriber
		MySubscriber<Integer> subscriber = new MySubscriber<>();

		// Create Processor and Subscriber
		MyFilterProcessor<String, String> filterProcessor = new MyFilterProcessor<>(s -> s.equals("x"));
		MyTransformProcessor<String, Integer> transformProcessor = new MyTransformProcessor<>(s -> Integer.parseInt(s));

		// Chain Processor and Subscriber
		publisher.subscribe(filterProcessor);
		filterProcessor.subscribe(transformProcessor);
		transformProcessor.subscribe(subscriber);

		// Publish items
		System.out.println("Publishing Items...");
		String[] items = { "1", "x", "2", "x", "3", "x" };
		Arrays.asList(items).stream().forEach(i -> publisher.submit(i));
		publisher.close();
		
		Thread.sleep(1000);

	}

}