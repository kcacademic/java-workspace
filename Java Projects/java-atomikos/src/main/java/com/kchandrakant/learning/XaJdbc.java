package com.kchandrakant.learning;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.jdbc.AtomikosDataSourceBean;

public class XaJdbc {

	public static void main(String[] args) throws IllegalStateException, SecurityException, RollbackException,
			HeuristicMixedException, HeuristicRollbackException, SystemException, SQLException, NotSupportedException {

		UserTransactionImp utx = new UserTransactionImp();

		AtomikosDataSourceBean ds1 = new AtomikosDataSourceBean();
		ds1.setXaDataSourceClassName("org.apache.derby.jdbc.EmbeddedXADataSource");
		Properties properties1 = new Properties();
		properties1.put("databaseName", "db1");
		properties1.put("createDatabase", "create");
		ds1.setXaProperties(properties1);
		ds1.setUniqueResourceName("XaDB1");
		ds1.setPoolSize(10); // optional
		ds1.setBorrowConnectionTimeout(10); // optional

		AtomikosDataSourceBean ds2 = new AtomikosDataSourceBean();
		ds2.setXaDataSourceClassName("org.apache.derby.jdbc.EmbeddedXADataSource");
		Properties properties2 = new Properties();
		properties2.put("databaseName", "db2");
		properties2.put("createDatabase", "create");
		ds2.setXaProperties(properties2);
		ds2.setUniqueResourceName("XaDB2");
		ds2.setPoolSize(10); // optional
		ds2.setBorrowConnectionTimeout(10); // optional

		boolean rollback = false;
		try {
			// begin a transaction
			utx.begin();
			// access the datasource and do any JDBC you like
			Connection conn1 = ds1.getConnection();
			Connection conn2 = ds2.getConnection();

			// perform operations on database
			String createTable = "create table Accounts ( "
					+ " account VARCHAR ( 20 ), owner VARCHAR(300), balance DECIMAL (19,0) )";
			String createRow = "insert into Accounts values ( " + "'account" + 1 + "' , 'owner" + 1 + "', 10000 )";
			Statement s1 = conn1.createStatement();
			try {
				s1.executeUpdate(createTable);
			} catch (Exception e) {
				System.out.println("Table exists");
			}
			s1.executeUpdate(createRow);
			String q1 = "update Accounts set balance = balance - " + 1000 + " where account ='account" + 1 + "'";
			s1.executeUpdate(q1);
			s1.close();
			Statement s2 = conn2.createStatement();
			try {
				s2.executeUpdate(createTable);
			} catch (Exception e) {
				System.out.println("Table exists");
			}
			s2.executeUpdate(createRow);
			String q2 = "update Accounts set balance = balance - " + 1000 + " where account ='account" + 1 + "'";
			s2.executeUpdate(q2);
			s2.close();

			// always close the connection for reuse in the
			// DataSource-internal connection pool
			conn1.close();
			conn2.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// an exception means we should not commit
			rollback = true;
		} finally {
			if (!rollback)
				utx.commit();
			else
				utx.rollback();
		}

		utx.begin();
		String query = "select balance from Accounts where account='" + "account" + 1 + "'";
		Connection conn = null;
		Statement s = null;
		ResultSet rs = null;
		conn = ds1.getConnection();
		s = conn.createStatement();
		rs = s.executeQuery(query);
		rs.next();
		System.out.println(rs.getLong(1));
		conn = ds2.getConnection();
		s = conn.createStatement();
		rs = s.executeQuery(query);
		rs.next();
		System.out.println(rs.getLong(1));
		utx.commit();

	}

}
