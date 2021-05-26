package com.kchandrakant.learning.validation;

interface State {
	State with(Transition tr);

	State transit(CharSequence c);

	boolean isFinal();
}