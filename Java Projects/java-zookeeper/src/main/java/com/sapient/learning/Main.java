package com.sapient.learning;

import com.sapient.learning.manager.ZKManager;
import com.sapient.learning.manager.impl.ZKManagerImpl;

public class Main {

	public static void main(String[] args) throws Exception {
		
		ZKManager manager = new ZKManagerImpl();
		
		manager.create("/MyFirstZNode/MyZNode", "Hello World!".getBytes());
	}
}