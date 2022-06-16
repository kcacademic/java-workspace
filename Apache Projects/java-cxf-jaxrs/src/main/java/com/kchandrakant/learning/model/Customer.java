package com.kchandrakant.learning.model;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Customer")
public class Customer {

	private int id;
	private String name;
	private List<Order> orders = new ArrayList<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	@GET
	@Path("{orderId}")
	public Order getOrder(@PathParam("orderId") int orderId) {
		return findById(orderId);
	}

	@POST
	public Response createOrder(Order order) {
		for (Order element : orders) {
			if (element.getId() == order.getId()) {
				return Response.status(Response.Status.CONFLICT).build();
			}
		}
		orders.add(order);
		return Response.ok(order).build();
	}

	@DELETE
	@Path("{orderId}")
	public Response deleteOrder(@PathParam("orderId") int orderId) {
		Order order = findById(orderId);
		if (order == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		orders.remove(order);
		return Response.ok().build();
	}

	private Order findById(int id) {
		for (Order order : orders) {
			if (order.getId() == id) {
				return order;
			}
		}
		return null;
	}

	@Override
	public int hashCode() {
		return id + name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Customer) && (id == ((Customer) obj).getId())
				&& (name.equals(((Customer) obj).getName()));
	}

}