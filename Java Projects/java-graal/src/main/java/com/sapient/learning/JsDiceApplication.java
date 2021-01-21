package com.sapient.learning;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

public class JsDiceApplication {

	public static void main(String[] args) throws Exception {
		if (args == null || args.length != 1) {
			System.err.println("Usage: roll <dice_expression>");
			System.err.println("Example: roll 2d20");
			System.exit(1);
		}

		System.out.println(parseDiceNotation(args[0]));
	}

	private static int parseDiceNotation(String expression) throws Exception {
		// load the javascript file from the classpath
		String diceRollerJs;
		try (Scanner scanner = new Scanner(
				JsDiceApplication.class.getResourceAsStream("/dice-roller.js"),
				StandardCharsets.UTF_8)) {
			diceRollerJs = scanner.useDelimiter("\\A").next();
		}

		// parse the javascript file with a new context
		Context context = Context.create("js");
		context.eval("js", diceRollerJs);

		// get a reference to the "roll" function
		Value rollFunction = context.getBindings("js").getMember("roll");
		// execute the function
		return rollFunction.execute(expression).asInt();
	}
}
