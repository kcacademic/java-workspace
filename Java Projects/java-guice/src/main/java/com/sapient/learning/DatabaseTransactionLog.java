package com.sapient.learning;

public class DatabaseTransactionLog implements TransactionLog {

	public void log(String transaction) {
		System.out.println(transaction);
	}

}
