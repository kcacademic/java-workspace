package com.kchandrakant.learning.client;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import com.kchandrakant.learning.data.DataPublisher;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import reactor.core.publisher.Flux;

public class RsocketClient {

	private final RSocket socket;

	public RsocketClient() {
		this.socket = RSocketFactory.connect().transport(TcpClientTransport.create("localhost", 7000)).start().block();
	}

	public String callBlocking(String string) {
		return socket
				.requestResponse(DefaultPayload.create(string))
				.map(Payload::getDataUtf8)
				.block();
	}

	public void sendData(int number) {
		List<String> dataList = new ArrayList<>();
		for (int i = 0; i < number; i++) {
			dataList.add(new Float(Math.random()).toString());
		}

		Flux.interval(Duration.ofMillis(50))
			.take(dataList.size())
			.map((Long l) -> {return DefaultPayload.create(dataList.get(l.intValue()));})
			.flatMap(socket::fireAndForget)
			.blockLast();
	}
	
    public Flux<String> getDataStream() {
        return socket
          .requestStream(DefaultPayload.create(""))
          .map(Payload::getDataUtf8)
          .onErrorReturn(null);
    }
    
    public Flux<String> openChannel(DataPublisher dataPublisher) {
        return socket.requestChannel(Flux.from(dataPublisher))
          .map(Payload::getDataUtf8)
          .onErrorReturn(null);
    }

	public void dispose() {
		this.socket.dispose();
	}

}