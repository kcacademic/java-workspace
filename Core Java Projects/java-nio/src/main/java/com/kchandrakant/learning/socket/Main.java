package com.kchandrakant.learning.socket;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {

		//Process server = EchoServer.start();
		Thread.sleep(1000);
		EchoClient client = EchoClient.start();
		
		String response = client.sendMessage("Kumar");
		System.out.println(response);
		
		//server.destroy();
		EchoClient.stop();

	}

}