package com.sapient.learning;

import java.util.concurrent.TimeUnit;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.sapient.learning.factory.HazelCastFactory;
import com.sapient.learning.model.Customer;

public class Main {

	public static void main(String[] args) {

		HazelcastInstance client = HazelCastFactory.getInstance();
		
		IMap<Object, Object> map = client.getMap("customers");
		
		Customer originalData = new Customer();

		originalData.setName("Alexandre Eleuterio Santos Lourenco");
		originalData.setPhone(33455676l);
		originalData.setSex("M");

		map.put(originalData.getPhone(), originalData, 5, TimeUnit.MINUTES);

		Customer fetchedData = (Customer) map.get(33455676l);
		
		System.out.println(fetchedData);
		
		
		HazelCastFactory.shutDown();
	}
}