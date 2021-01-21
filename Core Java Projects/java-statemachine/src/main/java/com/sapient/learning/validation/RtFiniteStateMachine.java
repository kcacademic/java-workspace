package com.sapient.learning.validation;

public class RtFiniteStateMachine implements FiniteStateMachine {

	private State current;

	public RtFiniteStateMachine(State initial) {
		this.current = initial;
	}

	public FiniteStateMachine switchState(CharSequence c) {
		return new RtFiniteStateMachine(this.current.transit(c));
	}

	public boolean canStop() {
		return this.current.isFinal();
	}
}