package org.mfs.reactor;

import org.junit.Test;

import reactor.core.publisher.Mono;

public class ReactorFunTest2 {

	@Test
	public void blockingTest() throws InterruptedException {

		Mono<String> foo = new ReactorFunTest2().foo();
		foo = addSuccessBehavior(foo, "foo");
		foo = addNextBehavior(foo, "foo");
		foo = addErrorBehavior(foo, "foo");
		foo = addEachBehavior(foo, "foo");
		foo.subscribe();
	}

	private Mono<String> addSuccessBehavior(Mono<String> mono, String name) {
		return mono.doOnSuccess(subscriber -> {
			System.out.printf("success %s", name);
			System.out.println();
		});
	}

	private Mono<String> addNextBehavior(Mono<String> mono, String name) {
		return mono.doOnNext(subscriber -> {
			System.out.printf("next %s", name);
			System.out.println();
		});
	}

	private Mono<String> addErrorBehavior(Mono<String> mono, String name) {
		return mono.doOnError(subscriber -> {
			System.out.printf("error %s", name);
			System.out.println();
		});
	}

	private Mono<String> addEachBehavior(Mono<String> mono, String name) {
		return mono.doOnEach(subscriber -> {
			System.out.printf("each %s", name);
			System.out.println();
		});
	}

	private Mono<String> foo() {

		return Mono.create(subscriber -> {

			for (int i = 0; i < 10; i++) {
				System.out.println("foo contando 1a vez: " + i);
			}

			Mono<String> bar = bar();
			bar = addSuccessBehavior(bar, "bar");
			bar = addNextBehavior(bar, "bar");
			bar = addErrorBehavior(bar, "bar");
			bar = addEachBehavior(bar, "bar");

			bar.subscribe(barSubscriber -> {
				subscriber.success(barSubscriber);
			});

			for (int i = 0; i < 5; i++) {
				System.out.println("foo contando 2a vez: " + i);
			}
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
