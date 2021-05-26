package com.kchandrakant.learning;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Recursion {

	private static Logger logger = Logger.getGlobal();

	public static void main(String[] args) {

		logger.log(Level.INFO, String.valueOf(iteration(5)));
		logger.log(Level.INFO, String.valueOf(recursion(5)));
		logger.log(Level.INFO, String.valueOf(tailRecursion(5, 1)));
		// logger.log(Level.INFO, String.valueOf(recursion(Integer.MAX_VALUE)));
		// logger.log(Level.INFO, String.valueOf(tailRecursion(Integer.MAX_VALUE, 1)));

	}

	private static Integer iteration(Integer number) {
		Integer result = 1;
		for (int i = 1; i <= number; i++)
			result = result * i;
		return result;
	}

	private static Integer recursion(Integer number) {

		return (number == 1) ? 1 : number * recursion(number - 1);

	}

	private static Integer tailRecursion(Integer number, Integer result) {

		return (number == 1) ? result : tailRecursion(number - 1, result * number);

	}

}
