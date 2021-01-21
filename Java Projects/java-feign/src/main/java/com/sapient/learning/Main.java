package com.sapient.learning;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.sapient.learning.client.CustomerClient;
import com.sapient.learning.domain.Customer;
import com.sapient.learning.domain.CustomerResource;

import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;

public class Main {

	public static void main(String[] args) throws ExecutionException, InterruptedException {

		CustomerClient customerClient = Feign.builder()
				.client(new OkHttpClient())
				.encoder(new GsonEncoder())
				.decoder(new GsonDecoder())
				.logger(new Slf4jLogger(CustomerClient.class))
				.logLevel(Logger.Level.FULL)
				.target(CustomerClient.class, "http://localhost:8081/api/customers");

		List<Customer> customers = customerClient.findAll()
				.stream()
				.map(CustomerResource::getCustomer)
				.collect(Collectors.toList());

		assertTrue(customers.size() > 2);
	}

}
