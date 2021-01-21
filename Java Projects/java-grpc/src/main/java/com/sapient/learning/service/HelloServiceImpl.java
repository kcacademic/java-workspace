package com.sapient.learning.service;

import com.sapient.learning.HelloRequest;
import com.sapient.learning.HelloResponse;
import com.sapient.learning.HelloServiceGrpc.HelloServiceImplBase;

import io.grpc.stub.StreamObserver;

public class HelloServiceImpl extends HelloServiceImplBase {

	@Override
	public void hello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
		System.out.println("Request received from client:\n" + request);

		String greeting = new StringBuilder().append("Hello, ").append(request.getFirstName()).append(" ")
				.append(request.getLastName()).toString();

		HelloResponse response = HelloResponse.newBuilder().setGreeting(greeting).build();

		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}
	
	@Override
	public void helloAgain(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
		System.out.println("Request again received from client:\n" + request);

		String greeting = new StringBuilder().append("Hello, ").append(request.getFirstName()).append(" ")
				.append(request.getLastName()).toString();

		HelloResponse response = HelloResponse.newBuilder().setGreeting(greeting).build();
		
		while(true) {
			responseObserver.onNext(response);
		}
		// responseObserver.onCompleted();
	}
}