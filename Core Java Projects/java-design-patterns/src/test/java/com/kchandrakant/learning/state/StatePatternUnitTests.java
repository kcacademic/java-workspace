package com.kchandrakant.learning.state;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.kchandrakant.learning.state.DeliveredState;
import com.kchandrakant.learning.state.OrderedState;
import com.kchandrakant.learning.state.Package;
import com.kchandrakant.learning.state.ReceivedState;

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