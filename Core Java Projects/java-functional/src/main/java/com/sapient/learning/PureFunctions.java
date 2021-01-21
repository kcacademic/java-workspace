package com.sapient.learning;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class PureFunctions {

	private static Logger logger = Logger.getGlobal();

	public static void main(String[] args) throws Exception {

		List<Integer> numbers = Arrays.asList(new Integer(10), new Integer(8));

		logger.log(Level.INFO, "Sum: " + sum(numbers));

	}

	private static Integer sum(List<Integer> numbers) {
		return numbers.stream().collect(Collectors.summingInt(Integer::intValue));
	}

}