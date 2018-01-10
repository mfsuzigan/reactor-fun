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
public class NovoTeste {
	public static void main(String[] args) throws InterruptedException {
		Mono<Object> joao = trapMonitorWrapByAnnontation(
				NovoTeste.rodaMetodoDeInsercao("joao demorado", Duration.ofSeconds(3)));
		Mono<Object> maria = NovoTeste.rodaMetodoDeInsercao("maria rapida sem monitoracao", Duration.ofSeconds(1));
		Mono<Object> joana = trapMonitorWrapByAnnontation(
				NovoTeste.rodaMetodoDeInsercao("joana rapida", Duration.ofSeconds(1)));
		joao.subscribe();
		joana.subscribe();
		maria.subscribe();
		Thread.sleep(5000);
	}

	public static Mono<Object> rodaMetodoDeInsercao(String name, Duration tempo) {
		return MonoMonitored.create(x -> {
			System.out.println("ola " + name);
			if (name.equals("joao demorado")) {
				x.error(null);
			}
			x.success();
		}).delaySubscription(tempo);
	}

	// esse trabalho de troca a implementacao do Mono vai ser feito na anotacao do
	// trap monitors
	static <T> Mono<T> trapMonitorWrapByAnnontation(Mono<T> source) {
		return new MonoMonitored(source);
	}
}

final class MonoMonitored<T> extends Mono<T> {
	private Mono source;

	MonoMonitored(Mono<T> source) {
		this.source = source;
	}

	@Override
	public void subscribe(final CoreSubscriber<? super T> s) {
		this.source.subscribe(new MeuSub(s));
	}
}

final class MeuSub<T> implements Subscriber<T> {
	private CoreSubscriber<T> source;

	MeuSub(CoreSubscriber<T> source) {
		this.source = source;
	}

	@Override
	public void onSubscribe(final Subscription s) {
		System.out.println("essa chamada vai ser monitorada");
		source.onSubscribe(s);
	}

	@Override
	public void onNext(final T t) {
		// esse evento eh usado no Flux, mas nao vamos monitorar cada resultado, apenas
		// sinais de erro ou terminado
		source.onNext(t);
	}

	@Override
	public void onError(final Throwable t) {
		System.out.println("Monitorei o erro");
		source.onError(t);
	}

	@Override
	public void onComplete() {
		System.out.println("Monitorei o sucesso");
		source.onComplete();
	}
}