package com.kchandrakant.learning;

import org.tensorflow.DataType;
import org.tensorflow.Graph;
import org.tensorflow.Output;
import org.tensorflow.Tensor;

public class GraphBuilder {
	
	private Graph g;
	
	GraphBuilder(Graph g) {
		this.g = g;
	}
	
	<T> Output<T> add(Output<T> x, Output<T> y) {
		return binaryOp("Add", x, y);
	}

	<T> Output<T> div(Output<T> x, Output<T> y) {
		return binaryOp("Div", x, y);
	}
	
	<T> Output<T> mul(Output<T> x, Output<T> y) {
		return binaryOp("Mul", x, y);
	}

	<T> Output<T> sub(Output<T> x, Output<T> y) {
		return binaryOp("Sub", x, y);
	}
	
	private <T> Output<T> binaryOp(String type, Output<T> in1, Output<T> in2) {
		return g.opBuilder(type, type).addInput(in1).addInput(in2).build().<T>output(0);
	}

	Output<String> constant(String name, byte[] value) {
		return constant(name, value, String.class);
	}

	Output<Integer> constant(String name, int value) {
		return constant(name, value, Integer.class);
	}

	Output<Integer> constant(String name, int[] value) {
		return constant(name, value, Integer.class);
	}

	Output<Float> constant(String name, float value) {
		return constant(name, value, Float.class);
	}
	
	Output<Double> constant(String name, double value) {
		return constant(name, value, Double.class);
	}
	
	private <T> Output<T> constant(String name, Object value, Class<T> type) {
		try (Tensor<T> t = Tensor.<T>create(value, type)) {
			return g.opBuilder("Const", name).setAttr("dtype", DataType.fromClass(type)).setAttr("value", t).build()
					.<T>output(0);
		}
	}

	
}