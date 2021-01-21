package com.sapient.learning.schema;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;

public class AvroSchemaBuilder {

	public Schema createCustomerSchema() {
		
		Schema address = SchemaBuilder
				.record("Address")
				.namespace("com.sapient.learning.model")
                .fields()
                	.requiredString("city")
                	.requiredString("zip")
                .endRecord();

		Schema customer = SchemaBuilder
				.record("Customer")
				.namespace("com.sapient.learning.model")
                .fields()
                	.requiredString("name")
                	.requiredString("email")
                	.name("address").type(address).noDefault()
                .endRecord();
		
		return customer;
	}
}