package com.sapient.learning;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Monads {

	private static Logger logger = Logger.getGlobal();

	public static void main(String[] args) {

		logger.log(Level.INFO,
				String.valueOf(Optional.of(2).flatMap(f -> Optional.of(3).flatMap(s -> Optional.of(f + s))).get()));

		logger.log(Level.INFO,
				String.valueOf(optionalAdd(Optional.of(new Integer(2)), Optional.of(new Integer(3))).get()));

	}

	private static Optional<Integer> optionalAdd(Optional<Integer> val1, Optional<Integer> val2) {
		return val1.flatMap(first -> val2.flatMap(second -> Optional.of(first + second)));
	}
}
