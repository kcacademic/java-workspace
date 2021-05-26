package com.kchandrakant.learning;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

public class SendRejectionMail implements JavaDelegate {

	public void execute(DelegateExecution execution) {
		System.out.println("Sending email for employee " + execution.getVariable("employee"));
	}

}