package com.sapient.learning;

import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationTests {

	private static Logger logger = LoggerFactory.getLogger(ApplicationTests.class);

	@BeforeAll
	public static void setUp() {
		logger.debug("Starting the tests at: {}", new Date());
	}

	@BeforeEach
	void setupThis() {
		logger.debug("Starting the test at: {}", new Date());
	}

	@Test
	public void testData() {
		Assertions.assertEquals("Hello World!", Application.getMessage());
	}

	@AfterEach
	void tearThis() {
		logger.debug("Finishing the test at: {}", new Date());
	}

	@AfterAll
	public static void tearDown() {
		logger.debug("Finishing the tests at: {}", new Date());
	}

}
