package com.kchandrakant.learning;

import static org.junit.Assert.assertEquals;

public class Main {

	public static void main(String[] args) {

		LeaveRequestState state = LeaveRequestState.SUBMITTED;

		state = state.nextState();
		assertEquals(LeaveRequestState.ESCALATED, state);

		state = state.nextState();
		assertEquals(LeaveRequestState.APPROVED, state);

		state = state.nextState();
		assertEquals(LeaveRequestState.APPROVED, state);

	}

}