package com.kchandrakant.learning.nlp;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.kchandrakant.learning.Main;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.util.Span;

public class NLPFunctions {

	public static List<String> getSentences(String input) throws IOException {

		InputStream is = NLPFunctions.class.getResourceAsStream("/models/en-sent.bin");
		SentenceModel model = new SentenceModel(is);
		SentenceDetectorME sdetector = new SentenceDetectorME(model);
		String sentences[] = sdetector.sentDetect(input);
		return Arrays.asList(sentences);
	}

	public static List<String> getNames(String input) throws IOException {

		SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
		String[] tokens = tokenizer.tokenize(input);
		InputStream inputStreamNameFinder = Main.class.getResourceAsStream("/models/en-ner-person.bin");
		TokenNameFinderModel model = new TokenNameFinderModel(inputStreamNameFinder);
		NameFinderME nameFinderME = new NameFinderME(model);
		List<Span> spans = Arrays.asList(nameFinderME.find(tokens));
		List<String> names = new ArrayList<String>();
		int k = 0;
		for (Span s : spans) {
			names.add("");
			for (int index = s.getStart(); index < s.getEnd(); index++) {
				names.set(k, names.get(k) + tokens[index]);
			}
			k++;
		}
		return names;
	}

}
