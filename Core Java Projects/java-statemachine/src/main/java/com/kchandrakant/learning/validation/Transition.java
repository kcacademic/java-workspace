package com.kchandrakant.learning.validation;

interface Transition {
	boolean isPossible(CharSequence c);

	State state();
}