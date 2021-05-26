package com.kchandrakant.learning;

import java.util.concurrent.ExecutionException;

import com.kchandrakant.learning.kafka.MyKafkaConsumer;
import com.kchandrakant.learning.kafka.MyKafkaProducer;

public class Main {

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		
		MyKafkaProducer producer = new MyKafkaProducer();
		producer.send("Kumar Chandrakant");
		
		MyKafkaConsumer consumer = new MyKafkaConsumer();
		consumer.subscribe();
		consumer.readMessage();
		
	}

}
