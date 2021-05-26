package com.kchandrakant.learning;

import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FunctionComposition {
	
	private static Logger logger = Logger.getGlobal();

	public static void main(String[] args) {

		Function<Double, Double> log = (value) -> Math.log(value);
		Function<Double, Double> sqrt = (value) -> Math.sqrt(value);

		Function<Double, Double> logThenSqrt = sqrt.compose(log);
		logger.log(Level.INFO, String.valueOf(logThenSqrt.apply(3.14)));

		Function<Double, Double> sqrtThenLog = sqrt.andThen(log);
		logger.log(Level.INFO, String.valueOf(sqrtThenLog.apply(3.14)));
	}

}
