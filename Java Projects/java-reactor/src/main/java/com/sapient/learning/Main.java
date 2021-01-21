package com.sapient.learning;

import java.util.function.Predicate;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		Flux<Integer> flux = Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
				.log().filter(new Predicate<Integer>() {
					@Override
					public boolean test(Integer t) {
						return t%2==0;
					}
				})
				.subscribeOn(Schedulers.parallel());

		Subscriber<Integer> subscriber = new Subscriber<Integer>() {
			@Override
			public void onSubscribe(Subscription s) {
				s.request(Long.MAX_VALUE);
			}

			@Override
			public void onNext(Integer i) {
				System.out.println("New data received: " + i);
			}

			@Override
			public void onError(Throwable t) {
				System.out.println("Error received: " + t.getMessage());
			}

			@Override
			public void onComplete() {
				System.out.println("All data emitted.");
			}
		};

		flux.subscribe(subscriber);
		
		Thread.sleep(1000);

	}

}