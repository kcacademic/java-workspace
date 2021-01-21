package com.sapient.learning;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class MyBenchmark {

	@Benchmark
	public void testMethod() {
		// This is a demo/sample template for building your JMH benchmarks. Edit as
		// needed.
		// Put your benchmark code here.
	}

	@Benchmark
	public String[] testJavaStandardSingleChar() {
		return "1,2,3,4,5,6,7,8,9,10".split(",");
	}

	@Benchmark
	public String[] testJavaStandardTwoChars() {
		return "1,.2,.3,.4,.5,.6,.7,.8,.9,.10".split(",\\.");
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(MyBenchmark.class.getSimpleName())
				.warmupIterations(1)
				.measurementIterations(1)
				.forks(1)
				.build();

		new Runner(opt).run();
	}

}
