package org.mfs.reactor;

import java.time.Duration;
import java.util.Scanner;

import org.junit.Test;

import reactor.core.publisher.Mono;

public class ReactorFunTest3 {

	@SuppressWarnings("resource")
	@Test
	public void blockingTest() {
		Mono<Object> joao = ReactorFunTest3.rodaMetodoDeInsercao("joao demorado", Duration.ofSeconds(3));
		Mono<Object> maria = ReactorFunTest3.rodaMetodoDeInsercao("maria rapida", Duration.ofSeconds(1));
		Mono<Object> joana = ReactorFunTest3.rodaMetodoDeInsercao("joana rapida", Duration.ofSeconds(1));
		// joao.subscribe();
		// joana.subscribe();
		// maria.subscribe();

		joao.block();
		joana.block();
		maria.block();

		new Scanner(System.in).nextLine();
	}

	public static Mono<Object> rodaMetodoDeInsercao(String name, Duration tempo) {
		return Mono.create(x -> {
			System.out.println("ola " + name);
		}).delaySubscription(tempo);
	}

}
