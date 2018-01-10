package org.mfs.reactor;

import java.time.Duration;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;

/**
 * @author mlixa
 * @version $Revision: $<br/>
 *          $Id: $
 * @since 02/01/18 15:47
 */
public class ReactorFunTest3 {
	public static void main(String[] args) throws InterruptedException {
		Mono<Object> joao = wrap(ReactorFunTest3.rodaMetodoDeInsercao("joao demorado", Duration.ofSeconds(3)));
		Mono<Object> maria = wrap(ReactorFunTest3.rodaMetodoDeInsercao("maria rapida", Duration.ofSeconds(1)));
		Mono<Object> joana = wrap(ReactorFunTest3.rodaMetodoDeInsercao("joana rapida", Duration.ofSeconds(1)));

		joao.subscribe();
		joana.subscribe();
		maria.subscribe();

		Thread.sleep(5000);
	}

	public static Mono<Object> rodaMetodoDeInsercao(String name, Duration tempo) {
		return MeuMono.create(subscriber -> {
			System.out.println("ola " + name);
		}).delaySubscription(tempo);
	}

	static <T> Mono<T> wrap(Mono<T> mono) {
		return new MeuMono(mono);
	}
}

final class MeuMono<T> extends Mono<T> {
	private Mono mono;

	MeuMono(Mono<T> source) {
		this.mono = source;
	}

	@Override
	public void subscribe(final CoreSubscriber<? super T> coreSubscriber) {
		this.mono.subscribe(new MeuSubscriber(coreSubscriber));
	}
}

final class MeuSubscriber<T> implements Subscriber<T> {
	private CoreSubscriber<T> coreSubscriber;

	MeuSubscriber(CoreSubscriber<T> source) {
		this.coreSubscriber = source;
	}

	@Override
	public void onSubscribe(final Subscription subscription) {
		System.out.println("teste 1");
		coreSubscriber.onSubscribe(subscription);
	}

	@Override
	public void onNext(final T t) {
		System.out.println("teste 2");
		coreSubscriber.onNext(t);
	}

	@Override
	public void onError(final Throwable t) {
		System.out.println("teste 3");
		coreSubscriber.onError(t);
	}

	@Override
	public void onComplete() {
		System.out.println("teste 4");
		coreSubscriber.onComplete();
	}
}