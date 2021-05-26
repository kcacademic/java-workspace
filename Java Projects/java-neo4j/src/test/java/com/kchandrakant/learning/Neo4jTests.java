package com.kchandrakant.learning;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.neo4j.ogm.session.Session;

import com.kchandrakant.learning.model.Customer;
import com.kchandrakant.learning.service.CustomerService;
import com.kchandrakant.learning.session.Neo4jSessionFactory;

public class Neo4jTests {

	private static Session session;
	private static CustomerService customerService;

	@BeforeAll
	public static void setUp() {
		session = Neo4jSessionFactory.getInstance().getEmbeddedSession();
		customerService = new CustomerService(session);
	}

	@Test
	public void testData() {
		customerService.createOrUpdate(new Customer(1L, "Kumar", "Chandrakant"));
		Customer result = customerService.find(1L);
		assertEquals(1L, result.getId());
	}
	
	@AfterAll
	public static void tearDown() {
		session.clear();
	}

}
