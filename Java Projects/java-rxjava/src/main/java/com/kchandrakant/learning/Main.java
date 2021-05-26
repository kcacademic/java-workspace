package com.kchandrakant.learning;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		Observable<Integer> observable = Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
				.filter(new Predicate<Integer>() {
					@Override
					public boolean test(Integer integer) throws Exception {
						return integer % 2 != 0;
					}
				});

		Observer<Integer> observer = new Observer<Integer>() {
			@Override
			public void onComplete() {
				System.out.println("All data emitted.");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println("Error received: " + e.getMessage());
			}

			@Override
			public void onNext(Integer s) {
				System.out.println("New data received: " + s);
			}

			@Override
			public void onSubscribe(Disposable d) {
				System.out.println("New subscription.");
			}
		};

		observable.subscribeOn(Schedulers.newThread()).observeOn(Schedulers.io()).subscribe(observer);

		Thread.sleep(1000);
	}

}