package com.sapient.learning.state;

public interface PackageState {

	void next(Package pkg);

	void prev(Package pkg);

	void printStatus();
}