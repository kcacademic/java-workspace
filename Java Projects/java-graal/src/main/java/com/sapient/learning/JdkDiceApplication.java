package com.sapient.learning;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class JdkDiceApplication {

	public static void main(String[] args) {
		if (args == null || args.length != 1) {
			System.err.println("Usage: roll <dice_expression>");
			System.err.println("Example: roll 2d20");
			System.exit(1);
		}

		System.out.println(parseDiceNotation(args[0]));
	}

	private static int parseDiceNotation(String expression) {

		// regex and match
		String simpleDiceRegex = "(?<numberOfDice>\\d+)?[dD](?<numberOfFaces>\\d+)";
		Matcher matcher = Pattern.compile(simpleDiceRegex).matcher(expression);

		// fail if no match
		if (!matcher.matches()) {
			throw new IllegalStateException(
					"Failed to parse dice expression: " + expression);
		}

		// default numberOfDice to 1
		String numberOfDiceString = matcher.group("numberOfDice");
		if (numberOfDiceString == null || numberOfDiceString.isEmpty()) {
			numberOfDiceString = "1";
		}
		int numberOfDice = Integer.parseInt(numberOfDiceString);
		int numberOfFaces = Integer.parseInt(matcher.group("numberOfFaces"));

		// roll!
		return IntStream.rangeClosed(1, numberOfDice)
				.map(index -> new Random().nextInt(numberOfFaces) + 1).sum();
	}
}
