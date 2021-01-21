package com.sapient.learning;

import java.io.FileNotFoundException;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.RulesEngineParameters;

import com.sapient.learning.rules.BuzzRule;
import com.sapient.learning.rules.CustomRule;
import com.sapient.learning.rules.FizzBuzzRule;
import com.sapient.learning.rules.FizzRule;
import com.sapient.learning.rules.NonFizzBuzzRule;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, Exception {

		// create a rules engine
		RulesEngineParameters parameters = new RulesEngineParameters().skipOnFirstAppliedRule(true);
		RulesEngine fizzBuzzEngine = new DefaultRulesEngine(parameters);

		// create rules
		Rules rules = new Rules();
		rules.register(new FizzRule());
		rules.register(new BuzzRule());
		rules.register(new FizzBuzzRule(new FizzRule(), new BuzzRule()));
		rules.register(new CustomRule());
		rules.register(new NonFizzBuzzRule());

		// fire rules
		Facts facts = new Facts();
		for (int i = 1; i <= 100; i++) {
			facts.put("number", i);
			fizzBuzzEngine.fire(rules, facts);
			System.out.println();
		}
	}

}