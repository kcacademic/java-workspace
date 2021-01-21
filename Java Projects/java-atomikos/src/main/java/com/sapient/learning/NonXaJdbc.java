package com.sapient.learning;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.jdbc.AtomikosNonXADataSourceBean;

public class NonXaJdbc {

	public static void main(String[] args) throws IllegalStateException, SecurityException, RollbackException,
			HeuristicMixedException, HeuristicRollbackException, SystemException {

		UserTransactionImp utx = new UserTransactionImp();

		AtomikosNonXADataSourceBean ds = new AtomikosNonXADataSourceBean();
		ds.setUniqueResourceName("NonXaDB");
		ds.setUrl("jdbc:derby:db;create=true");
		ds.setDriverClassName("org.apache.derby.jdbc.EmbeddedDriver");
		ds.setPoolSize(1); // optional
		ds.setBorrowConnectionTimeout(60); // optional

		boolean rollback = false;
		try {
			// begin a transaction
			utx.begin();
			// access the datasource and do any JDBC you like
			Connection conn = ds.getConnection();

			// perform operations on database
			Statement s = conn.createStatement();
			String q = "select balance from Accounts where account='" + "account" + 1 + "'";
			ResultSet rs = s.executeQuery(q);
			if (rs == null || !rs.next())
				throw new Exception("Account not found: " + 1);
			System.out.println(rs.getLong(1));
			s.close();

			// always close the connection for reuse in the
			// DataSource-internal connection pool
			conn.close();
		} catch (Exception e) {
			// an exception means we should not commit
			rollback = true;
		} finally {
			if (!rollback)
				utx.commit();
			else
				utx.rollback();
		}

	}

}
