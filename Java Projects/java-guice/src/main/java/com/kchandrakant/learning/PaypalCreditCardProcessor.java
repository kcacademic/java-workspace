package com.kchandrakant.learning;

public class PaypalCreditCardProcessor implements CreditCardProcessor {
	
	public String process(String card) {
		return "Processed card: " + card;
	}

}
