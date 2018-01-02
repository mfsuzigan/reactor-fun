package org.mfs.reactor;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactorFunTest {

	private static List<String> words = Arrays.asList("the", "quick", "brown", "fox", "jumped", "over", "the", "lazy",
			"dog");

	@Test
	public void simpleCreation() {
		Flux<String> fewWords = Flux.just("Hello", "World");
		Flux<String> manyWords = Flux.fromIterable(words);

		fewWords.subscribe(System.out::println);
		System.out.println();
		manyWords.subscribe(System.out::println);
	}

	@Test
	public void findingMissingLetter() {
		Flux<String> manyLetters = Flux.fromIterable(words).flatMap(word -> Flux.fromArray(word.split(""))).distinct()
				.sort()
				.zipWith(Flux.range(1, Integer.MAX_VALUE), (string, count) -> String.format("%2d. %s", count, string));
		manyLetters.subscribe(System.out::println);
	}

	@Test
	public void blockingTest() {

		for (int i = 0; i < 5; i++) {
			System.out.println("blockingTeste, contando" + i);
		}

		System.out.println("blockingTeste, bloqueando...");

		new ReactorFunTest().foo().block();

		System.out.println("blockingTeste, desbloqueando");

		for (int i = 0; i < 3; i++) {
			System.out.println("blockingTeste, contando" + i);
		}
	}

	private Mono<String> foo() {

		return Mono.create(subscriber -> {

			for (int i = 0; i < 10; i++) {
				System.out.println("foo contando: " + i);
			}

			bar().subscribe();

			for (int i = 0; i < 20; i++) {
				System.out.println("foo contando: " + i);
			}

			subscriber.success("foo finalizado");
		});

	}

	private Mono<String> bar() {

		return Mono.create(subscriber -> {

			for (int i = 0; i < 10; i++) {
				System.out.println("bar contando: " + i);
			}

			subscriber.success("bar finalizado");
		});
	}

}
