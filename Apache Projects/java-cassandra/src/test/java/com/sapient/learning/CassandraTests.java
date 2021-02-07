package com.sapient.learning;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.utils.UUIDs;
import com.sapient.learning.connector.CassandraConnector;
import com.sapient.learning.model.Customer;
import com.sapient.learning.repository.CustomerRepository;
import com.sapient.learning.repository.KeyspaceRepository;

public class CassandraTests {

	private KeyspaceRepository schema;

	private CustomerRepository customerRepository;

	private Session session;

	final String KEYSPACE_NAME = "testShop";

	@BeforeClass
	public static void init() throws ConfigurationException, TTransportException, IOException, InterruptedException {
		
		EmbeddedCassandraServerHelper.startEmbeddedCassandra(20000L);
	}

	@Before
	public void connect() {
		CassandraConnector client = new CassandraConnector();
		client.connect("127.0.0.1", 9142);
		this.session = client.getSession();
		schema = new KeyspaceRepository(session);
		schema.createKeyspace(KEYSPACE_NAME, "SimpleStrategy", 1);
		schema.useKeyspace(KEYSPACE_NAME);
		customerRepository = new CustomerRepository(session);
	}

	@Test
	public void whenCreatingATable_thenCreatedCorrectly() {
		customerRepository.deleteTableCustomer();
		customerRepository.createTableCustomer();

		ResultSet result = session.execute("SELECT * FROM " + KEYSPACE_NAME + "." + CustomerRepository.TABLE_NAME + ";");
		
		List<String> columnNames = result.getColumnDefinitions().asList().stream().map(cl -> cl.getName())
				.collect(Collectors.toList());
		assertEquals(columnNames.size(), 3);
		assertTrue(columnNames.contains("id"));
		assertTrue(columnNames.contains("firstname"));
		assertTrue(columnNames.contains("lastname"));
	}

	@Test
	public void whenAddingANewCustomer_thenCustomerExists() {
		customerRepository.deleteTableCustomer();
		customerRepository.deleteTableCustomerByFirstname();
		customerRepository.createTableCustomer();
		customerRepository.createTableCustomerByFirstName();
		
		Customer customer = new Customer(UUIDs.timeBased(), "Kumar", "Chandrakant", null);
		customerRepository.insertCustomer(customer);
		
		List<Customer> customers = customerRepository.selectAll();
		assertEquals(1, customers.size());
		assertTrue(customers.stream().anyMatch(b -> b.getFirstName().equals("Kumar")));

		List<Customer> customersByFirstName = customerRepository.selectAllByFirstName("Kumar");
		assertEquals(1, customersByFirstName.size());
		assertTrue(customersByFirstName.stream().anyMatch(b -> b.getFirstName().equals("Kumar")));
	}

	@AfterClass
	public static void cleanup() {
		EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
	}
}
