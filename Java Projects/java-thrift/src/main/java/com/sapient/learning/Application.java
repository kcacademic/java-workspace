package com.sapient.learning;

import org.apache.thrift.transport.TTransportException;

import com.sapient.learning.server.HelloServiceServer;

public class Application {

	public static void main(String[] args) throws TTransportException {
		
		HelloServiceServer server = new HelloServiceServer();
        server.start();

	}

}
