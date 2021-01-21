package com.sapient.learning.rules;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Priority;
import org.jeasy.rules.annotation.Rule;

@Rule
public class CustomRule {

	@Condition
	public boolean isBuzz(@Fact("number") Integer number) {
		return number % 3 == 0;
	}

	@Action
	public void printBuzz() {
		System.out.print("custom");
	}

	@Priority
	public int getPriority() {
		return 3;
	}
}
