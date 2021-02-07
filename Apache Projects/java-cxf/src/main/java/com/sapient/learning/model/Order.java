package com.sapient.learning.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Order")
public class Order {

	private int id;
	private String name;

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

	@Override
	public int hashCode() {
		return id + name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Order) && (id == ((Order) obj).getId()) && (name.equals(((Order) obj).getName()));
	}

}