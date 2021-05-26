package com.kchandrakant.learning.validation;

public interface FiniteStateMachine {
	FiniteStateMachine switchState(CharSequence c);

	boolean canStop();
}
