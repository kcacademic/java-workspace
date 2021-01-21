package com.sapient.learning.session;

import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

public class Neo4jSessionFactory {

	private static SessionFactory boltSessionFactory = new SessionFactory(
			new Configuration.Builder().uri("bolt://localhost").credentials("neo4j", "sapient").build(),
			"com.sapient.learning");
	private static SessionFactory httpSessionFactory = new SessionFactory(
			new Configuration.Builder().uri("http://localhost").credentials("neo4j", "sapient").build(),
			"com.sapient.learning");
	private static SessionFactory embeddedSessionFactory = new SessionFactory(
			new Configuration.Builder().build(),
			"com.sapient.learning");

	private static Neo4jSessionFactory factory = new Neo4jSessionFactory();

	private Neo4jSessionFactory() {

	}

	public static Neo4jSessionFactory getInstance() {
		return factory;
	}

	public Session getBoltSession() {
		return boltSessionFactory.openSession();
	}

	public Session getHttpSession() {
		return httpSessionFactory.openSession();
	}
	
	public Session getEmbeddedSession() {
		return embeddedSessionFactory.openSession();
	}

}
