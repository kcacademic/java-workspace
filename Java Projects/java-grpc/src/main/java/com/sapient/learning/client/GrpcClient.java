package com.sapient.learning.client;

import java.util.Iterator;

import com.sapient.learning.HelloRequest;
import com.sapient.learning.HelloResponse;
import com.sapient.learning.HelloServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcClient {
	public static void main(String[] args) throws InterruptedException {
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080).usePlaintext().build();

		HelloServiceGrpc.HelloServiceBlockingStub blockingStub = HelloServiceGrpc.newBlockingStub(channel);

		HelloResponse helloResponse = blockingStub
				.hello(HelloRequest.newBuilder().setFirstName("Kumar").setLastName("Chandrakant").build());

		System.out.println("Response received from server:\n" + helloResponse);
		
		Iterator<HelloResponse> helloResponseAgain = blockingStub
				.helloAgain(HelloRequest.newBuilder().setFirstName("Kumar").setLastName("Chandrakant").build());
		
		while(helloResponseAgain.hasNext()) {
			System.out.println("Response again received from server:\n" + helloResponseAgain.next());
			Thread.sleep(1000);
		}
		

		channel.shutdown();
	}
}