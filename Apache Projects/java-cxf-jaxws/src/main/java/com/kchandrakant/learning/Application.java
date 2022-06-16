package com.kchandrakant.learning;

import javax.xml.ws.Endpoint;

import com.kchandrakant.learning.repository.MyServiceImpl;

public class Application {
	public static void main(String args[]) throws InterruptedException {
		MyServiceImpl implementor = new MyServiceImpl();
		String address = "http://localhost:8080/kchandrakant";
		Endpoint.publish(address, implementor);
		System.out.println("Server ready...");
		Thread.sleep(60 * 1000);
		System.out.println("Server exiting");
		System.exit(0);
	}
}
