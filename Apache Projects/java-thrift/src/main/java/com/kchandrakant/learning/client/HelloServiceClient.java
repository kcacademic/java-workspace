package com.kchandrakant.learning.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.kchandrakant.learning.HelloService;

public class HelloServiceClient {

	public static void main(String[] args) {
		try {
			TTransport transport;

			transport = new TSocket("localhost", 9090);
			transport.open();

			TProtocol protocol = new TBinaryProtocol(transport);
			HelloService.Client client = new HelloService.Client(protocol);

			System.out.println("Calling remote method...");

			boolean result = client.ping();

			System.out.println(result);

			System.out.println("done.");

			transport.close();

		} catch (TTransportException e) {
			e.printStackTrace();
		} catch (TException x) {
			x.printStackTrace();
		}

	}
}