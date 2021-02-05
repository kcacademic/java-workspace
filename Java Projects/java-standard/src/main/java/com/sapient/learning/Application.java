package com.sapient.learning;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

	private static Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {

		logger.debug("Starting the application at: {}", new Date());

	}
	
	public static String getMessage() {
		return "Hello World!";
	}
	
}