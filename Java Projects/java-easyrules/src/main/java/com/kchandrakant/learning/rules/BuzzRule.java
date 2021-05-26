package com.kchandrakant.learning.rules;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Priority;
import org.jeasy.rules.annotation.Rule;

@Rule
public class BuzzRule {

	@Condition
	public boolean isBuzz(@Fact("number") Integer number) {
		return number % 7 == 0;
	}

	@Action
	public void printBuzz() {
		System.out.print("buzz");
	}

	@Priority
	public int getPriority() {
		return 2;
	}
}
