package com.sapient.learning;

import java.io.IOException;

import com.sapient.learning.nlp.NLPFunctions;

public class Main {

	public static void main(String[] args) throws IOException {

		System.out.println(NLPFunctions
				.getNames("John is 26 years old. His best friend's name is Leonard. He has a sister named Penny."));

		System.out.println(NLPFunctions
				.getSentences("John is 26 years old. His best friend's name is Leonard. He has a sister named Penny."));

	}

}
