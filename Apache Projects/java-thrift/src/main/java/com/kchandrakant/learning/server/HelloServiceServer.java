package com.kchandrakant.learning.server;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

import com.kchandrakant.learning.processor.HelloServiceImpl;
import com.kchandrakant.learning.HelloService;

public class HelloServiceServer {

	private TServer server;

	public void start() throws TTransportException {
		TServerTransport serverTransport = new TServerSocket(9090);
		server = new TSimpleServer(new TServer.Args(serverTransport)
				.processor(new HelloService.Processor<>(new HelloServiceImpl())));

		System.out.print("Starting the server... ");

		server.serve();

		System.out.println("done.");
	}

	public void stop() {
		if (server != null && server.isServing()) {
			System.out.print("Stopping the server... ");

			server.stop();

			System.out.println("done.");
		}
	}
}