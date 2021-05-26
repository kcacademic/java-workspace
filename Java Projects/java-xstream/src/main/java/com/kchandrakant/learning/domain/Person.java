package com.kchandrakant.learning.domain;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.kchandrakant.learning.convertor.SingleValueCalendarConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.thoughtworks.xstream.converters.basic.BooleanConverter;

import lombok.Data;

@Data
public class Person {
	@XStreamAlias("givenName")
	private String firstname;
	@XStreamAlias("surName")
	private String lastname;
	
	private PhoneNumber phone;
	
	@XStreamOmitField
	private PhoneNumber fax;
	
	@XStreamConverter(SingleValueCalendarConverter.class)
	private Calendar created = new GregorianCalendar();
	
	@XStreamAsAttribute
	@XStreamConverter(value=BooleanConverter.class, booleans={false}, strings={"yes", "no"})
	private boolean important;
}