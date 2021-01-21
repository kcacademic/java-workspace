package com.sapient.learning;

public class PaypalCreditCardProcessor implements CreditCardProcessor {
	
	public String process(String card) {
		return "Processed card: " + card;
	}

}
