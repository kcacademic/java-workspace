package com.sapient.learning.client;

import java.util.List;

import com.sapient.learning.domain.Customer;
import com.sapient.learning.domain.CustomerResource;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface CustomerClient {
	@RequestLine("GET /{isbn}")
	CustomerResource findByIsbn(@Param("isbn") String isbn);

	@RequestLine("GET")
	List<CustomerResource> findAll();

	@RequestLine("POST")
	@Headers("Content-Type: application/json")
	void create(Customer customer);
}
