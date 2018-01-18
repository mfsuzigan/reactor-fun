package org.mfs.reactor;

import java.util.Arrays;

import org.junit.Test;

import reactor.core.publisher.Flux;

public class ReactorFunTest5 {

	@Test
	public void t1() {
		// Mono.fromCallable(() ->
		// getNames()).log().doOnSuccess(System.out::println).subscribe();
		getNames().sort().zipWith(getAdjectives(), (name, adjective) -> buildPhrase(name, adjective))
				.doOnError(throwable -> System.out.println("oops: " + throwable.getMessage()))
				.doOnNext(System.out::println).subscribe();
	}

	private Flux<String> getNames() {
		return Flux.fromIterable(Arrays.asList("charlie", "bravo", "delta", "alfa", "echo"));
	}

	private String buildPhrase(String name, String adjective) {
		return adjective + " " + name + " is coming";
	}

	private Flux<String> getAdjectives() {
		Flux.fromIterable(Arrays.asList("fierce", "callous", "brazen", "seditious", "unfathomable"));
		return Flux.error(new RuntimeException("yikes!"));
	}
}
