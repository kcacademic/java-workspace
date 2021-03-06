package com.kchandrakant.learning;

import java.util.Iterator;

import org.neo4j.ogm.session.Session;

import com.kchandrakant.learning.model.Customer;
import com.kchandrakant.learning.service.CustomerService;
import com.kchandrakant.learning.session.Neo4jSessionFactory;

public class Main {
	
	public static void main(String[] args) {
		
		// Using HTTP Driver
		Session httpSession = Neo4jSessionFactory.getInstance().getHttpSession();
		CustomerService httpService = new CustomerService(httpSession);
		Iterator<Customer> httpIterator = httpService.findAll().iterator();
		while(httpIterator.hasNext()) {
			System.out.println(httpIterator.next());
		}
		
		// Using Bolt Driver
		Session boltSession = Neo4jSessionFactory.getInstance().getBoltSession();
		CustomerService boltService = new CustomerService(boltSession);
		Iterator<Customer> boltIterator = boltService.findAll().iterator();
		while(boltIterator.hasNext()) {
			System.out.println(boltIterator.next());
		}

	}
	
}