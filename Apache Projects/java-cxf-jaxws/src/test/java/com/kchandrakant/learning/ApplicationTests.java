package com.kchandrakant.learning;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import org.junit.Before;
import org.junit.Test;

import com.kchandrakant.learning.model.Student;
import com.kchandrakant.learning.model.StudentImpl;
import com.kchandrakant.learning.repository.MyService;
import com.kchandrakant.learning.repository.MyServiceImpl;

public class ApplicationTests {
	private static QName SERVICE_NAME = new QName("http://repository.learning.kchandrakant.com/", "MyService");
	private static QName PORT_NAME = new QName("http://repository.learning.kchandrakant.com/", "MyServicePort");

	private Service service;
	private MyService myService;
	private MyServiceImpl myServiceImpl;

	{
		service = Service.create(SERVICE_NAME);
		final String endpointAddress = "http://localhost:8080/kchandrakant";
		service.addPort(PORT_NAME, SOAPBinding.SOAP11HTTP_BINDING, endpointAddress);
	}

	@Before
	public void reinstantiateBaeldungInstances() {
		myServiceImpl = new MyServiceImpl();
		myService = service.getPort(PORT_NAME, MyService.class);
	}

	@Test
	public void whenUsingHelloMethod_thenCorrect() {
		final String endpointResponse = myService.hello("Hello!");
		final String localResponse = myService.hello("Hello!");
		assertEquals(localResponse, endpointResponse);
	}

	@Test
	public void whenUsingHelloStudentMethod_thenCorrect() {
		final Student student = new StudentImpl("John Doe");
		final String endpointResponse = myServiceImpl.helloStudent(student);
		final String localResponse = myServiceImpl.helloStudent(student);
		assertEquals(localResponse, endpointResponse);
	}

	@Test
	public void usingGetStudentsMethod_thenCorrect() {
		final Student student1 = new StudentImpl("Adam");
		myService.helloStudent(student1);

		final Student student2 = new StudentImpl("Eve");
		myService.helloStudent(student2);

		final Map<Integer, Student> students = myService.getStudents();
		assertEquals("Adam", students.get(1).getName());
		assertEquals("Eve", students.get(2).getName());
	}
}
