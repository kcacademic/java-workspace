package com.kchandrakant.learning.repository;

import com.datastax.driver.core.Session;

public class KeyspaceRepository {
	private Session session;

	public KeyspaceRepository(Session session) {
		this.session = session;
	}

	public void createKeyspace(String keyspaceName, String replicatioonStrategy, int numberOfReplicas) {
		StringBuilder sb = new StringBuilder("CREATE KEYSPACE IF NOT EXISTS ").append(keyspaceName)
				.append(" WITH replication = {").append("'class':'").append(replicatioonStrategy)
				.append("','replication_factor':").append(numberOfReplicas).append("};");

		final String query = sb.toString();

		session.execute(query);
	}

	public void useKeyspace(String keyspace) {
		session.execute("USE " + keyspace);
	}

	public void deleteKeyspace(String keyspaceName) {
		StringBuilder sb = new StringBuilder("DROP KEYSPACE ").append(keyspaceName);

		final String query = sb.toString();

		session.execute(query);
	}
}