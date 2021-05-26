package com.kchandrakant.learning.adaptor;

import org.bson.Document;

import com.kchandrakant.learning.model.Customer;

public class CustomerAdaptor {

	public static final Document toDocument(Customer customer) {
		return new Document("_id", customer.getId())
				.append("firstName", customer.getFirstName())
				.append("books", customer.getLastName());
	}

}
