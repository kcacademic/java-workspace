package com.kchandrakant.learning;

import com.kchandrakant.learning.manager.ZKManager;
import com.kchandrakant.learning.manager.impl.ZKManagerImpl;

public class Main {

	public static void main(String[] args) throws Exception {
		
		ZKManager manager = new ZKManagerImpl();
		
		manager.create("/MyFirstZNode/MyZNode", "Hello World!".getBytes());
	}
}