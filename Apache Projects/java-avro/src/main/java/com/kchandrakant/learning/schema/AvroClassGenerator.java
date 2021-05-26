package com.kchandrakant.learning.schema;

import java.io.File;
import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.compiler.specific.SpecificCompiler;

public class AvroClassGenerator {
	public void generateAvroClasses() throws IOException {
		SpecificCompiler compiler = new SpecificCompiler(
				new Schema.Parser().parse(new File("src/main/resources/schema.avsc")));
		compiler.compileToDestination(new File("src/main/resources"), new File("src/main/java"));
	}
}