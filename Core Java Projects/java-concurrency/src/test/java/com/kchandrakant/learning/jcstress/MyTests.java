package com.kchandrakant.learning.jcstress;

import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE;
import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE_INTERESTING;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Arbiter;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.I_Result;

@JCStressTest
@Outcome(id = "1", expect = ACCEPTABLE_INTERESTING, desc = "One update lost.")
@Outcome(id = "2", expect = ACCEPTABLE, desc = "Both updates.")
@State
public class MyTests {

	int v;

	@Actor
	public void actor1() {
		v++;
	}

	@Actor
	public void actor2() {
		v++;
	}

	@Arbiter
	public void arbiter(I_Result r) {
		r.r1 = v;
	}

}
