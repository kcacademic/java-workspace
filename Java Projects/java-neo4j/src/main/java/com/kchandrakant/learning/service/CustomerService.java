package com.kchandrakant.learning.service;

import java.util.Collection;

import org.neo4j.ogm.session.Session;

import com.kchandrakant.learning.model.Customer;

public class CustomerService {

	private static final int DEPTH_LIST = 0;
	private static final int DEPTH_ENTITY = 1;

	private Session session;

	public CustomerService(Session session) {
		this.session = session;
	}

	public Collection<Customer> findAll() {
		return session.loadAll(Customer.class, DEPTH_LIST);
	}

	public Customer find(Long id) {
		return session.load(Customer.class, id, DEPTH_ENTITY);
	}

	public void delete(Long id) {
		session.delete(session.load(Customer.class, id));
	}

	public Customer createOrUpdate(Customer entity) {
		session.save(entity, DEPTH_ENTITY);
		return find(entity.getId());
	}

}
