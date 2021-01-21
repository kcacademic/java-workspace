package com.sapient.learning;

import com.sapient.learning.domain.Person;
import com.sapient.learning.domain.PhoneNumber;
import com.thoughtworks.xstream.XStream;

public class Main {

	public static void main(String[] args) {

		XStream xstream = new XStream();

		xstream.alias("person", Person.class);
		xstream.alias("phonenumber", PhoneNumber.class);
		xstream.aliasField("callingCode", PhoneNumber.class, "code");
		
		//xstream.processAnnotations(Person.class);
		//xstream.processAnnotations(PhoneNumber.class);
		
		xstream.autodetectAnnotations(true);

		Person joe = new Person();
		joe.setFirstname("Joe");
		joe.setLastname("Walnes");
		joe.setPhone(new PhoneNumber(123, "1234-456"));
		joe.setFax(new PhoneNumber(123, "9999-999"));
		
		String xml = xstream.toXML(joe);
		System.out.println(xml);

		Person newJoe = (Person) xstream.fromXML(xml);
		System.out.println(newJoe);

	}

}
