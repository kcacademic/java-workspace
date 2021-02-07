package com.sapient.learning.kafka;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

public class MyKafkaConsumer {

	private String brokerList = "localhost:9092";

	private String topic = "votes";

	Consumer<Long, String> consumer;

	public MyKafkaConsumer() {

		final Properties kafkaProps = new Properties();
		kafkaProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
		kafkaProps.put(ConsumerConfig.GROUP_ID_CONFIG, "MyConsumer");
		kafkaProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.StringDeserializer");
		kafkaProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.StringDeserializer");

		consumer = new KafkaConsumer<>(kafkaProps);

	}

	public void subscribe() {

		consumer.subscribe(Collections.singletonList(topic));

	}

	public void readMessage() {
		consumer.seekToBeginning(Collections.<TopicPartition>emptyList());

		ConsumerRecords<Long, String> consumerRecords = consumer.poll(Duration.ofMillis(1000));

		consumerRecords.forEach(record -> {
			System.out.printf("Consumer Record:(%d, %s, %d, %d)\n", record.key(), record.value(), record.partition(),
					record.offset());
		});
	}

}
