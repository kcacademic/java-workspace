package com.kchandrakant.learning;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FirstClassFunctions {
	
	private static Logger logger = Logger.getGlobal();

	public static void main(String[] args) throws Exception {

		List<Integer> numbers = Arrays.asList(new Integer(10), new Integer(8));

		logger.log(Level.INFO, "Raw: " + numbers);

		Collections.sort(numbers, new Comparator<Integer>() {
			@Override
			public int compare(Integer n1, Integer n2) {
				return n1.compareTo(n2);
			}
		});

		Collections.sort(numbers, (n1, n2) -> n1.compareTo(n2));

		logger.log(Level.INFO, "Sorted: " + numbers);

	}

}