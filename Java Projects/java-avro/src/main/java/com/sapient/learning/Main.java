package com.sapient.learning;

import java.io.PrintWriter;

import com.sapient.learning.model.Address;
import com.sapient.learning.model.Customer;
import com.sapient.learning.schema.AvroClassGenerator;
import com.sapient.learning.schema.AvroSchemaBuilder;
import com.sapient.learning.serdeser.AvroDeserializer;
import com.sapient.learning.serdeser.AvroSerializer;

public class Main {

	public static void main(String[] args) throws Exception {
		
		// 1. Building the Schema
		AvroSchemaBuilder schemaBuilder = new AvroSchemaBuilder();
		String schema = schemaBuilder.createCustomerSchema().toString();
		PrintWriter out = new PrintWriter("src/main/resources/schema.avsc");
		out.println(schema);
		out.close();
		
		// 2. Generating the Schema Classes
		AvroClassGenerator classGenerator = new AvroClassGenerator();
		classGenerator.generateAvroClasses();
		
		// 3. Creating the Test Data
		Customer customer = new Customer();
		customer.setName("Kumar Chandrakant");
		customer.setEmail("kchandrakant@gmail.com");
		Address address = new Address();
		address.setCity("New Delhi");
		address.setZip("110011");
		customer.setAddress(address);
		
		// 4. Actual Serialization
		AvroSerializer serializer = new AvroSerializer();
		byte[] data = serializer.serealizeAvroHttpRequestJSON(customer);
		
		// 5. Actual De-serialization
	    AvroDeserializer deserializer = new AvroDeserializer();
	    Customer actualCustomer = deserializer.deSerealizeAvroHttpRequestJSON(data);
	    System.out.println(actualCustomer);
		
	}
}