package com.sapient.learning.server;

import java.io.IOException;

import com.sapient.learning.service.HelloServiceImpl;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class GrpcServer {
	public static void main(String[] args) throws IOException, InterruptedException {
		Server server = ServerBuilder.forPort(8080).addService(new HelloServiceImpl()).build();

		System.out.println("Starting server...");
		server.start();
		System.out.println("Server started!");
		server.awaitTermination();
	}
}
