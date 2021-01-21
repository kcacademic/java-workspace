package com.sapient.learning.repository;

import java.util.ArrayList;
import java.util.List;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.sapient.learning.model.Address;
import com.sapient.learning.model.Customer;

public class CustomerRepository {

	public static final String TABLE_NAME = "customers";
	
	public static final String TABLE_NAME_BY_FIRST_NAME = TABLE_NAME + "ByFirstName";
	
	public static final String TABLE_NAME_WITH_ADDRESS = TABLE_NAME + "WithAddress";
	
	public static final String UDT_TYPE_NAME = "address";

	private Session session;

	public CustomerRepository(Session session) {
		this.session = session;
	}

	public void createTableCustomer() {
		StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
				.append(TABLE_NAME)
				.append("(")
				.append("id uuid PRIMARY KEY, ")
				.append("firstName text,")
				.append("lastName text")
				.append(");");

		final String query = sb.toString();
		session.execute(query);
	}
	
    public void createTableCustomerByFirstName() {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
        		.append(TABLE_NAME_BY_FIRST_NAME)
        		.append("(")
        		.append("id uuid, ")
        		.append("firstName text,")
        		.append("lastName text")
        		.append("PRIMARY KEY (firstName, id)")
        		.append(");");

        final String query = sb.toString();
        session.execute(query);
    }
    
    public void createTableCustomerWithAddress() {
    	StringBuilder query1 = new StringBuilder("CREATE TYPE IF NOT EXISTS ")
    			.append(UDT_TYPE_NAME)
    			.append("(")
    			.append("city text, ")
    			.append("postcode text")
    			.append(");");
        session.execute(query1.toString());
    	
        StringBuilder query2 = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
        		.append(TABLE_NAME_WITH_ADDRESS)
        		.append("(")
        		.append("id uuid PRIMARY KEY, ")
        		.append("firstName text, ")
        		.append("lastName text, ")
        		.append("address FROZEN<address>")
        		.append(");");
        session.execute(query2.toString());

    }

	public void insertCustomer(Customer customer) {
        StringBuilder sb = new StringBuilder("BEGIN BATCH ")
        		.append("INSERT INTO ")
        		.append(TABLE_NAME)
        		.append("(id, firstName, lastName) ")
        		.append("VALUES (").append(customer.getId()).append(", '").append(customer.getFirstName()).append("', '").append(customer.getLastName()).append("');")
        		.append("INSERT INTO ")
        		.append(TABLE_NAME_BY_FIRST_NAME).append("(id, firstName) ").append("VALUES (").append(customer.getId()).append(", '").append(customer.getFirstName()).append("');")
                .append("APPLY BATCH;");

        final String query = sb.toString();
        session.execute(query);
	}
	
	public void insertCustomerWithAddress(Customer customer) {
        StringBuilder sb = new StringBuilder("INSERT INTO ")
        		.append(TABLE_NAME_WITH_ADDRESS)
        		.append("(id, firstName, lastName, address) ")
        		.append("VALUES (")
        			.append(customer.getId()).append(", '")
        			.append(customer.getFirstName()).append("', '")
        			.append(customer.getLastName()).append("', ")
        			.append("{")
        				.append("city: '" + customer.getAddress().getCity()).append("' ")
        				.append(",")
        				.append("postcode: '" + customer.getAddress().getPost()).append("' ")
        			.append("}")
        		.append(");");

        final String query = sb.toString();
        session.execute(query);
	}
	
	public List<Customer> selectAll() {
		StringBuilder sb = new StringBuilder("SELECT * FROM ").append(TABLE_NAME);

		final String query = sb.toString();
		ResultSet rs = session.execute(query);

		List<Customer> customers = new ArrayList<Customer>();

		for (Row r : rs) {
			Customer customer = new Customer(r.getUUID("id"), r.getString("firstName"), r.getString("lastName"), null);
			customers.add(customer);
		}
		return customers;
	}
	
	public List<Customer> selectAllWithAddress() {
		StringBuilder sb = new StringBuilder("SELECT * FROM ").append(TABLE_NAME_WITH_ADDRESS);

		final String query = sb.toString();
		ResultSet rs = session.execute(query);

		List<Customer> customers = new ArrayList<Customer>();

		for (Row r : rs) {
			Customer customer = new Customer(r.getUUID("id"), r.getString("firstName"), r.getString("lastName"), new Address(r.getUDTValue("address").getString("city"), r.getUDTValue("address").getString("postcode")));
			customers.add(customer);
		}
		return customers;
	}

	public List<Customer> selectAllByFirstName(String firstName) {
		StringBuilder sb = new StringBuilder("SELECT * FROM ")
				.append(TABLE_NAME_BY_FIRST_NAME)
				.append(" WHERE firstName = '")
				.append(firstName)
				.append("';");

		final String query = sb.toString();

		ResultSet rs = session.execute(query);

		List<Customer> customers = new ArrayList<Customer>();

		for (Row r : rs) {
			Customer s = new Customer(r.getUUID("id"), r.getString("firstName"), null, null);
			customers.add(s);
		}

		return customers;
	}
	
    public void deleteTableCustomer() {
        StringBuilder sb = new StringBuilder("DROP TABLE IF EXISTS ").append(TABLE_NAME);

        final String query = sb.toString();
        session.execute(query);
    }
    
    public void deleteTableCustomerByFirstname() {
        StringBuilder sb = new StringBuilder("DROP TABLE IF EXISTS ").append(TABLE_NAME_BY_FIRST_NAME);

        final String query = sb.toString();
        session.execute(query);
    }
    
    public void deleteTableCustomerWithAddress() {
        StringBuilder query1 = new StringBuilder("DROP TABLE IF EXISTS ").append(TABLE_NAME_WITH_ADDRESS);
        session.execute(query1.toString());
        
        StringBuilder query2 = new StringBuilder("DROP TYPE IF EXISTS ").append(UDT_TYPE_NAME);
        session.execute(query2.toString());
    }
    
}