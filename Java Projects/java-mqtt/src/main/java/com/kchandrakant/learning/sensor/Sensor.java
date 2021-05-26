package com.kchandrakant.learning.sensor;

import java.util.Random;
import java.util.concurrent.Callable;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Sensor implements Callable<Void> {

	public static final String TOPIC = "environment";

	private IMqttClient client;
	private Random rnd = new Random();

	public Sensor(IMqttClient client) {
		this.client = client;
	}

	@Override
	public Void call() throws Exception {

		if (!client.isConnected()) {
			System.out.println("[I31] Client not connected.");
			return null;
		}

		while (true) {
			Thread.sleep(1000);
			MqttMessage msg = readTemperature();
			msg.setQos(0);
			msg.setRetained(true);
			client.publish(TOPIC, msg);
		}

		//return null;
	}

	private MqttMessage readTemperature() {
		double temp = 80 + rnd.nextDouble() * 20.0;
		byte[] payload = String.format("temperature,site=hall value=%04.2f", temp).getBytes();
		MqttMessage msg = new MqttMessage(payload);
		return msg;
	}
}
