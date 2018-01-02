package org.mfs.reactor;

import org.junit.Test;

import reactor.core.publisher.Mono;

public class ReactorFunTest2 {

	@Test
	public void blockingTest() {

		for (int i = 0; i < 5; i++) {
			System.out.println("blockingTeste contando 1a vez: " + i);
		}

		System.out.println("blockingTeste, bloqueando!");
		new ReactorFunTest2().foo().block();
		System.out.println("blockingTeste, desbloqueado!");

		for (int i = 0; i < 3; i++) {
			System.out.println("blockingTeste, contando 2a vez: " + i);
		}

		System.out.println("blockingTeste finalizado");
	}

	private Mono<String> foo() {

		return Mono.create(subscriber -> {

			for (int i = 0; i < 10; i++) {
				System.out.println("foo contando 1a vez: " + i);
			}

			bar().subscribe();

			for (int i = 0; i < 5; i++) {
				System.out.println("foo contando 2a vez: " + i);
			}

			subscriber.success("foo finalizado");
		});

	}

	private Mono<String> bar() {

		return Mono.create(subscriber -> {

			for (int i = 0; i < 10; i++) {
				System.out.println("bar contando unica vez: " + i);
			}

			subscriber.success("bar finalizado");
		});
	}

}
