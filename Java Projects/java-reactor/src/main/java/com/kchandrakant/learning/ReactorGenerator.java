package com.kchandrakant.learning;

import reactor.core.publisher.Flux;

public class ReactorGenerator {

	public static void main(String[] args) {

		getIntegerFlux()
			//.delayElements(Duration.ofMillis(0))
			.skipUntil(x -> x == 11)
			//.skip(1000)
			.doOnNext(c -> System.out.println(c))
			.subscribe();

	}

	private static Flux<Integer> getIntegerFlux() {

		return Flux.generate(() -> 1, (state, sink) -> {
			int value = state.intValue();
			sink.next(value);
			if (value == 50) {
				sink.complete();
			}
			return state + 1;
		});

	}

}