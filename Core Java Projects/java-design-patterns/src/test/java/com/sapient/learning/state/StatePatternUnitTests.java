package com.sapient.learning.state;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class StatePatternUnitTests {

	@Test
	public void givenNewPackage_whenPackageReceived_thenStateReceived() {
		Package pkg = new Package();

		assertThat(pkg.getState(), instanceOf(OrderedState.class));
		pkg.nextState();

		assertThat(pkg.getState(), instanceOf(DeliveredState.class));
		pkg.nextState();

		assertThat(pkg.getState(), instanceOf(ReceivedState.class));
	}

	@Test
	public void givenDeliveredPackage_whenPrevState_thenStateOrdered() {
		Package pkg = new Package();
		pkg.setState(new DeliveredState());
		pkg.previousState();

		assertThat(pkg.getState(), instanceOf(OrderedState.class));
	}
}