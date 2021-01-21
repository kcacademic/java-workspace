package com.sapient.learning;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Random;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class ColdObservable {

	public static void main(String[] args) throws InterruptedException, UnknownHostException, IOException {

		Single<String> single = Single.just("Hello World!");

		single.map(String::toUpperCase).subscribe(s -> System.out.println(s), Throwable::printStackTrace);

		Thread.sleep(1000);

		Observable<String> coldStream = Observable.just("a", "b", "c", "d", "e");

		coldStream.map(String::toUpperCase).scan(new StringBuilder(), StringBuilder::append)
				.subscribe(s -> System.out.println(s), Throwable::printStackTrace, () -> System.out.println("Done"));

		Random random = new Random();

		Observable<Integer> coldStreamUnbounded = Observable.fromCallable(() -> random.nextInt()).repeat();

		coldStreamUnbounded.subscribe(s -> System.out.println(s), Throwable::printStackTrace,
				() -> System.out.println("Done"));

		Flowable<Integer> coldStreamFlowable = Observable.range(1, 1000000).toFlowable(BackpressureStrategy.ERROR);

		coldStreamFlowable.onBackpressureBuffer(10000000).observeOn(Schedulers.newThread()).observeOn(Schedulers.newThread()).subscribe(s -> {
			System.out.println(s);
		}, Throwable::printStackTrace, () -> System.out.println("Done"));

		Thread.sleep(1000000000);

	}

}
