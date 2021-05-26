package com.kchandrakant.learning.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;

import javax.sql.DataSource;

import com.kchandrakant.learning.model.Customer;

public class CustomerDao {

	private static final int MAX_RETRY_COUNT = 3;
	private static final String SAVEPOINT_NAME = "cockroach_restart";
	private static final String RETRY_SQL_STATE = "40001";
	private static final boolean FORCE_RETRY = false;

	private final DataSource ds;

	public CustomerDao(DataSource ds) {
		this.ds = ds;
	}

	/**
	 * Used to test the retry logic in 'runSQL'. It is not necessary in production
	 * code. Note that this calls an internal CockroachDB function that can only be
	 * run by the 'root' user, and will fail with an insufficient privileges error
	 * if you try to run it as user 'maxroach'.
	 */
	public void testRetryHandling() {
		if (CustomerDao.FORCE_RETRY) {
			runSQL("SELECT crdb_internal.force_retry('1s':::INTERVAL)");
		}
	}

	/**
	 * Run SQL code in a way that automatically handles the transaction retry logic
	 * so we don't have to duplicate it in various places.
	 *
	 * @param sqlCode a String containing the SQL code you want to execute. Can have
	 *                placeholders, e.g., "INSERT INTO accounts (id, balance) VALUES
	 *                (?, ?)".
	 *
	 * @param args    String Varargs to fill in the SQL code's placeholders.
	 * @return Integer Number of rows updated, or -1 if an error is thrown.
	 */
	public Integer runSQL(String sqlCode, String... args) {

		// This block is only used to emit class and method names in
		// the program output. It is not necessary in production
		// code.
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		StackTraceElement elem = stacktrace[2];
		String callerClass = elem.getClassName();
		String callerMethod = elem.getMethodName();

		int rv = 0;

		try (Connection connection = ds.getConnection()) {

			// We're managing the commit lifecycle ourselves so we can
			// automatically issue transaction retries.
			connection.setAutoCommit(false);

			int retryCount = 0;

			while (retryCount < MAX_RETRY_COUNT) {

				Savepoint sp = connection.setSavepoint(SAVEPOINT_NAME);

				// This block is only used to test the retry logic.
				// It is not necessary in production code. See also
				// the method 'testRetryHandling()'.
				if (FORCE_RETRY) {
					forceRetry(connection); // SELECT 1
				}

				try (PreparedStatement pstmt = connection.prepareStatement(sqlCode)) {

					// Loop over the args and insert them into the
					// prepared statement based on their types. In
					// this simple example we classify the argument
					// types as "integers" and "everything else"
					// (a.k.a. strings).
					for (int i = 0; i < args.length; i++) {
						int place = i + 1;
						String arg = args[i];

						try {
							int val = Integer.parseInt(arg);
							pstmt.setInt(place, val);
						} catch (NumberFormatException e) {
							pstmt.setString(place, arg);
						}
					}

					if (pstmt.execute()) {
						// We know that `pstmt.getResultSet()` will
						// not return `null` if `pstmt.execute()` was
						// true
						ResultSet rs = pstmt.getResultSet();
						ResultSetMetaData rsmeta = rs.getMetaData();
						int colCount = rsmeta.getColumnCount();

						// This printed output is for debugging and/or demonstration
						// purposes only. It would not be necessary in production code.
						System.out.printf("\n%s.%s:\n    '%s'\n", callerClass, callerMethod, pstmt);

						while (rs.next()) {
							for (int i = 1; i <= colCount; i++) {
								String name = rsmeta.getColumnName(i);
								String type = rsmeta.getColumnTypeName(i);

								// In this "bank account" example we know we are only handling
								// integer values (technically 64-bit INT8s, the CockroachDB
								// default). This code could be made into a switch statement
								// to handle the various SQL types needed by the application.
								if (type == "int8") {
									int val = rs.getInt(name);

									// This printed output is for debugging and/or demonstration
									// purposes only. It would not be necessary in production code.
									System.out.printf("    %-8s => %10s\n", name, val);
								}
							}
						}
					} else {
						int updateCount = pstmt.getUpdateCount();
						rv += updateCount;

						// This printed output is for debugging and/or demonstration
						// purposes only. It would not be necessary in production code.
						System.out.printf("\n%s.%s:\n    '%s'\n", callerClass, callerMethod, pstmt);
					}

					connection.releaseSavepoint(sp);
					connection.commit();
					break;

				} catch (SQLException e) {

					if (RETRY_SQL_STATE.equals(e.getSQLState())) {
						System.out.printf(
								"retryable exception occurred:\n    sql state = [%s]\n    message = [%s]\n    retry counter = %s\n",
								e.getSQLState(), e.getMessage(), retryCount);
						connection.rollback(sp);
						retryCount++;
						rv = -1;
					} else {
						rv = -1;
						throw e;
					}
				}
			}
		} catch (SQLException e) {
			System.out.printf("BasicExampleDAO.runSQL ERROR: { state => %s, cause => %s, message => %s }\n",
					e.getSQLState(), e.getCause(), e.getMessage());
			rv = -1;
		}

		return rv;
	}

	/**
	 * Helper method called by 'testRetryHandling'. It simply issues a "SELECT 1"
	 * inside the transaction to force a retry. This is necessary to take the
	 * connection's session out of the AutoRetry state, since otherwise the other
	 * statements in the session will be retried automatically, and the client (us)
	 * will not see a retry error. Note that this information is taken from the
	 * following test:
	 * https://github.com/cockroachdb/cockroach/blob/master/pkg/sql/logictest/testdata/logic_test/manual_retry
	 *
	 * @param connection Connection
	 */
	private void forceRetry(Connection connection) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement("SELECT 1")) {
			statement.executeQuery();
		}
	}

	/**
	 * Creates a fresh, empty customers table in the database.
	 */
	public void createCustomers() {
		runSQL("CREATE TABLE IF NOT EXISTS customers (id INT PRIMARY KEY, firstname VARCHAR (50), lastname VARCHAR (50))");
	};

	/**
	 * Update customers by passing in a Map of (ID, firstName) pairs.
	 *
	 */
	public int updateCustomers(List<Customer> customers) {
		int rows = 0;
		for (Customer customer : customers) {

			String id = customer.getId().toString();
			String firstName = customer.getFirstName();
			String lastName = customer.getLastName();

			String[] args = { id, firstName, lastName };
			rows += runSQL("INSERT INTO customers (id, firstname, lastname) VALUES (?, ?, ?)", args);
		}
		return rows;
	}

	/**
	 * Read out a subset of customers from the data store.
	 *
	 */
	public int readAccounts(int limit) {
		return runSQL("SELECT id, firstname, lastname FROM customers LIMIT ?", Integer.toString(limit));
	}

	/**
	 * Perform any necessary cleanup of the data store so it can be used again.
	 */
	public void tearDown() {
		runSQL("DROP TABLE customers;");
	}
}
