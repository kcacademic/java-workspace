package com.sapient.learning.validation;

interface State {
	State with(Transition tr);

	State transit(CharSequence c);

	boolean isFinal();
}