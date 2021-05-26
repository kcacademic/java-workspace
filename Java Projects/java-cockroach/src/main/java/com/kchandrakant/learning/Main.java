package com.kchandrakant.learning;

import java.util.ArrayList;
import java.util.List;

import org.postgresql.ds.PGSimpleDataSource;

import com.kchandrakant.learning.dao.CustomerDao;
import com.kchandrakant.learning.model.Customer;

public class Main {

	public static void main(String[] args) {

		// Configure the database connection.
		PGSimpleDataSource ds = new PGSimpleDataSource();
		ds.setServerName("localhost");
		ds.setPortNumber(26257);
		ds.setDatabaseName("store");
		ds.setUser("manager");
		ds.setPassword(null);
		ds.setReWriteBatchedInserts(true); // add `rewriteBatchedInserts=true` to pg connection string
		ds.setApplicationName("BasicExample");

		// Create DAO.
		CustomerDao dao = new CustomerDao(ds);

		// Test our retry handling logic if FORCE_RETRY is true. This
		// method is only used to test the retry logic. It is not
		// necessary in production code.
		dao.testRetryHandling();

		// Set up the 'accounts' table.
		dao.createCustomers();

		// Insert a few accounts "by hand", using INSERTs on the backend.
		List<Customer> customers = new ArrayList<Customer>();
		customers.add(new Customer(1L, "Kumar", "Chandrakant"));
		customers.add(new Customer(2L, "Kumar", "Nishant"));
		int updatedCustomers = dao.updateCustomers(customers);
		System.out.printf("CustomerDAO.updateCustomers:\n    => %s total updated customers\n", updatedCustomers);

		// Print out 10 account values.
		int customersRead = dao.readAccounts(10);
		System.out.printf("CustomerDAO.readCustomers:\n    => %s total read customers\n", customersRead);

		// Drop the 'accounts' table so this code can be run again.
		dao.tearDown();
	}
}