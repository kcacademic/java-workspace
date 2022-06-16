package com.kchandrakant.learning.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.kchandrakant.learning.model.Customer;
import com.kchandrakant.learning.model.Order;

@Path("myshop")
@Produces("text/xml")
public class CustomerRepository {
	private Map<Integer, Customer> customers = new HashMap<>();

	{
		Order order1 = new Order();
		Order order2 = new Order();
		order1.setId(1);
		order1.setName("Order A");
		order2.setId(2);
		order2.setName("Order B");

		List<Order> customer1Orders = new ArrayList<>();
		customer1Orders.add(order1);
		customer1Orders.add(order2);

		Customer customer1 = new Customer();
		Customer customer2 = new Customer();
		customer1.setId(1);
		customer1.setName("Kumar Chandrakant");
		customer1.setOrders(customer1Orders);
		customer2.setId(2);
		customer2.setName("Kumar Nishant");

		customers.put(1, customer1);
		customers.put(2, customer2);
	}

	@GET
	@Path("customers/{customerId}")
	public Customer getCustomer(@PathParam("customerId") int customerId) {
		return findById(customerId);
	}

	@PUT
	@Path("customers/{customerId}")
	public Response updateCustomer(@PathParam("customerId") int customerId, Customer customer) {
		Customer existingCustomer = findById(customerId);
		if (existingCustomer == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		if (existingCustomer.equals(customer)) {
			return Response.notModified().build();
		}
		customers.put(customerId, customer);
		return Response.ok().build();
	}

	@Path("customers/{customerId}/orders")
	public Customer pathToOrder(@PathParam("customerId") int customerId) {
		return findById(customerId);
	}

	private Customer findById(int id) {
		for (Map.Entry<Integer, Customer> course : customers.entrySet()) {
			if (course.getKey() == id) {
				return course.getValue();
			}
		}
		return null;
	}
}