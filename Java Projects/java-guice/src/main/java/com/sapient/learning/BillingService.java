package com.sapient.learning;

import com.google.inject.Inject;

class BillingService {
	private final CreditCardProcessor processor;
	private final TransactionLog transactionLog;

	@Inject
	BillingService(CreditCardProcessor processor, TransactionLog transactionLog) {
		this.processor = processor;
		this.transactionLog = transactionLog;
	}

	public String chargeOrder(String order, String creditCard) {
		transactionLog.log(processor.process(creditCard));
		return "Processed order: " + order;
	}
}