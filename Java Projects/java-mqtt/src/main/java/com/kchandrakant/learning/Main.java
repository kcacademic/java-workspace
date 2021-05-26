package com.kchandrakant.learning;

import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

import com.kchandrakant.learning.sensor.Sensor;

public class Main {

	public static void main(String[] args) throws Exception {
		
        String publisherId = UUID.randomUUID().toString();
        MqttClient publisher = new MqttClient("tcp://localhost:1883",publisherId);
        
        String subscriberId = UUID.randomUUID().toString();
        MqttClient subscriber = new MqttClient("tcp://localhost:1883",subscriberId);
        
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        
        
        subscriber.connect(options);
        publisher.connect(options);
        
        CountDownLatch receivedSignal = new CountDownLatch(1);
        
        subscriber.subscribe(Sensor.TOPIC, (topic, msg) -> {
            byte[] payload = msg.getPayload();
            System.out.println("[I46] Message received: topic=" + topic + "; payload={}" + new String(payload));
            receivedSignal.countDown();
        });
        
        
        Callable<Void> target = new Sensor(publisher);
        target.call();

        receivedSignal.await(1, TimeUnit.MINUTES);
		
	}
}