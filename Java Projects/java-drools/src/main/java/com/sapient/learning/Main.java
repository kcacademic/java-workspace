package com.sapient.learning;

import java.io.IOException;

import org.kie.api.runtime.KieSession;

import com.sapient.learning.config.DroolsBeanFactory;
import com.sapient.learning.model.Applicant;
import com.sapient.learning.model.SuggestedRole;

public class Main {

	public static void main(String[] args) throws IOException {

		SuggestedRole suggestedRole = new SuggestedRole();
		KieSession kieSession = DroolsBeanFactory.getKieSession();
		kieSession.setGlobal("suggestedRole", suggestedRole);

		kieSession.insert(new Applicant("David", 37, 1600000.0, 11));
		kieSession.fireAllRules();
		System.out.println(suggestedRole.getRole());
		
		kieSession.insert(new Applicant("John", 37, 1200000.0, 8));
		kieSession.fireAllRules();
		System.out.println(suggestedRole.getRole());
		
		kieSession.insert(new Applicant("Davis", 37, 800000.0, 3));
		kieSession.fireAllRules();
		System.out.println(suggestedRole.getRole());
		
		kieSession.insert(new Applicant("John", 37, 1200000.0, 5));
		kieSession.fireAllRules();
		System.out.println(suggestedRole.getRole());
	}

}
