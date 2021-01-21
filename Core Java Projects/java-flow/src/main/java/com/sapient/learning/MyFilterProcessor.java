package com.sapient.learning;

import java.util.concurrent.Flow.Processor;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Predicate;

public class MyFilterProcessor<T, R> extends SubmissionPublisher<R> implements Processor<T, R> {

	private Predicate<T> predicate;
	private Subscription subscription;

	public MyFilterProcessor(Predicate<T> predicate) {
		super();
		this.predicate = predicate;
	}

	@Override
	public void onSubscribe(Subscription subscription) {
		this.subscription = subscription;
		subscription.request(1);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onNext(T item) {
		if (!predicate.test(item))
			submit((R) item);
		subscription.request(1);
	}

	@Override
	public void onError(Throwable t) {
		t.printStackTrace();
	}

	@Override
	public void onComplete() {
		close();
	}
}
