package com.kchandrakant.learning.processor;

import java.util.Collections;
import java.util.List;

import org.apache.thrift.TException;

import com.kchandrakant.learning.HelloResource;
import com.kchandrakant.learning.HelloException;
import com.kchandrakant.learning.HelloService;

public class HelloServiceImpl implements HelloService.Iface {

	@Override
	public HelloResource get(int id) throws HelloException, TException {
		return new HelloResource();
	}

	@Override
	public void save(HelloResource resource) throws HelloException, TException {

	}

	@Override
	public List<HelloResource> getList() throws HelloException, TException {
		return Collections.emptyList();
	}

	@Override
	public boolean ping() throws HelloException, TException {
		return true;
	}
}
