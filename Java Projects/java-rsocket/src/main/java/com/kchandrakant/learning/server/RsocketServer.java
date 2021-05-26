package com.kchandrakant.learning.server;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kchandrakant.learning.data.DataPublisher;

import io.rsocket.AbstractRSocket;
import io.rsocket.Payload;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.server.TcpServerTransport;
import io.rsocket.util.DefaultPayload;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class RsocketServer {

	private static final Logger LOGGER = LoggerFactory.getLogger(RsocketServer.class);

	private final DataPublisher dataPublisher = new DataPublisher();

	public static void main(String[] args) {

		new RsocketServer();
		while (true) {
		}

	}

	private final Disposable server;

	public RsocketServer() {
		this.server = RSocketFactory.receive().acceptor((setupPayload, reactiveSocket) -> Mono.just(new RSocketImpl()))
				.transport(TcpServerTransport.create("localhost", 7000)).start()
				.doOnNext(x -> LOGGER.info("Server started")).subscribe();
	}

	public void dispose() {
		this.server.dispose();
	}

	private class RSocketImpl extends AbstractRSocket {

		@Override
		public Mono<Payload> requestResponse(Payload payload) {
			try {
				return Mono.just(DefaultPayload.create("Hello: " + payload.getDataUtf8()));
			} catch (Exception x) {
				return Mono.error(x);
			}
		}

		@Override
		public Mono<Void> fireAndForget(Payload payload) {
			
			try {
				dataPublisher.publish(payload);
				return Mono.empty();
			} catch (Exception x) {
				return Mono.error(x);
			}
		}

		@Override
		public Flux<Payload> requestStream(Payload payload) {
			
			return Flux.from(dataPublisher);
		}
		
		@Override
		public Flux<Payload> requestChannel(Publisher<Payload> payloads) {
			
		    Flux.from(payloads)
		      .subscribe(dataPublisher::publish);
		    
		    return Flux.from(dataPublisher);
		}
	}
}
