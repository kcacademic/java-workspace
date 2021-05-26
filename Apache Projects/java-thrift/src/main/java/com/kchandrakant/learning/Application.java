package com.kchandrakant.learning;

import org.apache.thrift.transport.TTransportException;

import com.kchandrakant.learning.server.HelloServiceServer;

public class Application {

	public static void main(String[] args) throws TTransportException {
		
		HelloServiceServer server = new HelloServiceServer();
        server.start();

	}

}
