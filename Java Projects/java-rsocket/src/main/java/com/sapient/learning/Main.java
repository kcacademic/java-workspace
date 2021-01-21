package com.sapient.learning;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sapient.learning.client.RsocketClient;
import com.sapient.learning.data.DataPublisher;
import com.sapient.learning.server.RsocketServer;

import io.rsocket.util.DefaultPayload;

public class Main {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws InterruptedException {

		RsocketServer server = new RsocketServer();

		RsocketClient client = new RsocketClient();

		String string = "Kumar Chandrakant";
		LOGGER.info(client.callBlocking(string));

		client.getDataStream().index().subscribe(tuple -> {
			LOGGER.info(tuple.getT2());
		}, err -> LOGGER.error(err.getMessage()));

		client.sendData(2);

		DataPublisher dataPublisher = new DataPublisher();
		client.openChannel(dataPublisher).index().subscribe(tuple -> {
			LOGGER.info(tuple.getT2());
		}, err -> LOGGER.error(err.getMessage()));
		dataPublisher.publish(DefaultPayload.create("Hello"));

		Thread.sleep(10000);

		client.dispose();
		
		server.dispose();

	}

}
