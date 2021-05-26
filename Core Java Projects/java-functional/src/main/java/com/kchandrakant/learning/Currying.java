package com.kchandrakant.learning;

import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Currying {

	private static Logger logger = Logger.getGlobal();

	public static void main(String[] args) {

		logger.log(Level.INFO, "My weight on Earth: " + weightOnEarth().apply(60.0));

		Function<Double, Function<Double, Double>> weight = mass -> gravity -> mass * gravity;

		Function<Double, Double> weightOnEarth = weight.apply(9.81);
		logger.log(Level.INFO, "My weight on Earth: " + weightOnEarth.apply(60.0));

		Function<Double, Double> weightOnMars = weight.apply(3.75);
		logger.log(Level.INFO, "My weight on Mars: " + weightOnMars.apply(60.0));

	}

	private static Function<Double, Double> weightOnEarth() {
		final double gravity = 9.81;
		return mass -> mass * gravity;
	}

}
