package com.sapient.learning;

import java.util.concurrent.ExecutionException;

import com.sapient.learning.kafka.MyKafkaConsumer;
import com.sapient.learning.kafka.MyKafkaProducer;

public class Main {

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		
		MyKafkaProducer producer = new MyKafkaProducer();
		producer.send("Kumar Chandrakant");
		
		MyKafkaConsumer consumer = new MyKafkaConsumer();
		consumer.subscribe();
		consumer.readMessage();
		
	}

}
