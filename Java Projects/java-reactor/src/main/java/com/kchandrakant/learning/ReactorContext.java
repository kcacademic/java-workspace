package com.kchandrakant.learning;

import java.util.HashMap;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

public class ReactorContext {

	public static void main(String[] args) throws InterruptedException {
		final Flux<String> greetings = Flux.just("Hubert", "Sharon")
				.flatMap(ReactorContext::nameToGreeting)
				.contextWrite(context -> Context.of("greetingWord", "hello"));
		greetings.subscribe(System.out::println);

	}

	private static Mono<String> nameToGreeting(final String name) {
		return Mono.deferContextual(
				c -> Mono.just(name)
				.filter(x -> c.hasKey("greetingWord"))
				.map(x -> c.get("greetingWord"))
				.flatMap(greetingWord -> Mono.just(greetingWord + " " + name + " " + "!!!")));
	}
	

}

class MyDomain {
	
}
