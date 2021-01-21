package com.sapient.learning;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.sapient.learning.domain.SimpleData;

public class ReferentialTransparency {

	private static Logger logger = Logger.getGlobal();

	public static void main(String[] args) {

		String data = new SimpleData().setData("Baeldung").getData();
		logger.log(Level.INFO, new SimpleData().setData("Baeldung").getData());
		logger.log(Level.INFO, data);
		logger.log(Level.INFO, "Baeldung");

	}

}
