package com.sapient.learning.simulation;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Simulation {

	public static void main(String[] args) throws MqttException, InterruptedException, ExecutionException {

		ExecutorService executorService = Executors.newCachedThreadPool();
		List<Callable<Boolean>> sensors = Arrays.asList(
				new Simulation.Sensor("london", "central", "ozone", "air-quality/ozone"),
				new Simulation.Sensor("london", "central", "co", "air-quality/co"),
				new Simulation.Sensor("london", "central", "so2", "air-quality/so2"),
				new Simulation.Sensor("london", "central", "no2", "air-quality/no2"),
				new Simulation.Sensor("london", "central", "aerosols", "air-quality/aerosols"));

		List<Future<Boolean>> futures = executorService.invokeAll(sensors);
		futures.stream().forEach(f -> {
			try {
				f.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		});

		executorService.shutdown();
	}

	public static class Sensor implements Callable<Boolean> {

		String city;
		String station;
		String pollutant;
		String topic;

		Sensor(String city, String station, String pollutant, String topic) {
			this.city = city;
			this.station = station;
			this.pollutant = pollutant;
			this.topic = topic;
		}

		@Override
		public Boolean call() throws Exception {
			MqttClient publisher = new MqttClient("tcp://localhost:1883", UUID.randomUUID().toString());
			MqttConnectOptions options = new MqttConnectOptions();
			options.setAutomaticReconnect(true);
			options.setCleanSession(true);
			options.setConnectionTimeout(10);
			publisher.connect(options);
			IntStream.range(0, 10).forEach(i -> {
				String payload = String.format("%1$s,city=%2$s,station=%3$s value=%4$04.2f", pollutant, city, station,
						ThreadLocalRandom.current().nextDouble(0, 100));

				System.out.println(payload);
				MqttMessage message = new MqttMessage(payload.getBytes());
				message.setQos(0);
				message.setRetained(true);
				try {
					publisher.publish(topic, message);
					Thread.sleep(1000);
				} catch (MqttException | InterruptedException e) {
					e.printStackTrace();
				}
			});
			return true;
		}

	}

}
