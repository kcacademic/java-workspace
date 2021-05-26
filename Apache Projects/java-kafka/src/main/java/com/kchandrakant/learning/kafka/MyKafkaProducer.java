package com.kchandrakant.learning.kafka;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class MyKafkaProducer {
	
    private String brokerList = "localhost:9092";
    
    private String sync = "sync";
    
    private String topic = "votes";
 
    private Producer<String, String> producer;
    
    public MyKafkaProducer() {
        Properties kafkaProps = new Properties();
        kafkaProps.put("bootstrap.servers", brokerList);
        kafkaProps.put("key.serializer", 
            "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("value.serializer", 
            "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("acks", "1");
        kafkaProps.put("retries", "1");
        kafkaProps.put("linger.ms", 5);
        producer = new KafkaProducer<>(kafkaProps);
    }
 
    public void send(String value) throws ExecutionException, 
            InterruptedException {
        if ("sync".equalsIgnoreCase(sync)) {
            sendSync(value);
        } else {
            sendAsync(value);
        }
    }
 
    private void sendSync(String value) throws ExecutionException,
            InterruptedException {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, value);
        producer.send(record).get();
 
    }
 
    private void sendAsync(String value) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, value);
 
        producer.send(record, (RecordMetadata recordMetadata, Exception e) -> {
            if (e != null) {
                e.printStackTrace();
            }
        });
    }
}