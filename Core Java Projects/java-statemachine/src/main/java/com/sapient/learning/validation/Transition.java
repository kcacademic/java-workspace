package com.sapient.learning.validation;

interface Transition {
	boolean isPossible(CharSequence c);

	State state();
}