package com.sapient.learning.validation;

public interface FiniteStateMachine {
	FiniteStateMachine switchState(CharSequence c);

	boolean canStop();
}
