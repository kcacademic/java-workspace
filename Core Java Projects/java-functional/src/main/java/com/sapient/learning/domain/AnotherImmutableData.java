package com.sapient.learning.domain;

public class AnotherImmutableData {

	private final Integer someOtherData;

	public AnotherImmutableData(final Integer someData) {
		this.someOtherData = someData;
	}

	public Integer getSomeOtherData() {
		return someOtherData;
	}

}
