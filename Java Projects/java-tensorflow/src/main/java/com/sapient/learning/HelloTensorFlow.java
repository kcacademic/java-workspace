package com.sapient.learning;

import org.tensorflow.Graph;
import org.tensorflow.Output;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

public class HelloTensorFlow {
	
	public static void main(String[] args) throws Exception {

		try (Graph graph = new Graph()) {
			GraphBuilder b = new GraphBuilder(graph);
			final Output<Double> output = b.add(b.constant("a", 3.0), b.constant("b", 2.0));
			try (Session sess = new Session(graph)) {
				Tensor<Double> tensor = sess.runner().fetch(output.op().name()).run().get(0).expect(Double.class);
				System.out.println(tensor.doubleValue());
			}
		}
		
	}
}