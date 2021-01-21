package com.sapient.learning.domain;

public class ImmutableData {

	private final String someData;

	private final AnotherImmutableData anotherImmutableData;

	public ImmutableData(final String someData, final AnotherImmutableData anotherImmutableData) {
		this.someData = someData;
		this.anotherImmutableData = anotherImmutableData;
	}

	public String getSomeData() {
		return someData;
	}

	public AnotherImmutableData getAnotherImmutableData() {
		return anotherImmutableData;
	}

}
