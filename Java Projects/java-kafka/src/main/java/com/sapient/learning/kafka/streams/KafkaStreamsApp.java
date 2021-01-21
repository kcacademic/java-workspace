package com.sapient.learning.kafka.streams;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;

public class KafkaStreamsApp {
	
    private static String brokerList = "localhost:9092";
    private static String inputTopic = "votes";
    private static String outputTopic = "summary";
	
	public static void main(String[] args) {
		
	    Properties streamsConfiguration = new Properties();
	    streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "stream-example");
	    streamsConfiguration.put(StreamsConfig.CLIENT_ID_CONFIG, "stream-example-client");
	    streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
	    streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
	    streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
	    
	    StreamsBuilder builder = new StreamsBuilder();
		
		KStream<String, String> textLines = builder.stream(inputTopic, Consumed.with(Serdes.String(), Serdes.String()));

		KTable<String, Long> wordCounts = textLines
		    .flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))
		    .groupBy((key, value) -> value)
		    .count();
		
		wordCounts.toStream().to(outputTopic, Produced.with(Serdes.String(), Serdes.Long()));
		
		KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfiguration);
		
		streams.cleanUp();
		
		streams.start();
		
		Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
	}

}
