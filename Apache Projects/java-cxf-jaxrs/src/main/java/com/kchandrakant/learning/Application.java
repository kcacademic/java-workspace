package com.kchandrakant.learning;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;

import com.kchandrakant.learning.repository.CustomerRepository;

public class Application {

	public static void main(String args[]) throws Exception {
		JAXRSServerFactoryBean factoryBean = new JAXRSServerFactoryBean();
		factoryBean.setResourceClasses(CustomerRepository.class);
		factoryBean.setResourceProvider(new SingletonResourceProvider(new CustomerRepository()));
		factoryBean.setAddress("http://localhost:8080/");
		Server server = factoryBean.create();

		System.out.println("Server ready...");
		Thread.sleep(60 * 1000);
		System.out.println("Server exiting");
		server.destroy();
		System.exit(0);
	}

}
