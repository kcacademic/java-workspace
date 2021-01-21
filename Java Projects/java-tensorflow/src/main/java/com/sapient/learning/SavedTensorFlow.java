package com.sapient.learning;

import org.tensorflow.SavedModelBundle;
import org.tensorflow.Tensor;

public class SavedTensorFlow {

	public static void main(String[] args) {
		
		SavedModelBundle model = SavedModelBundle.load("C:\\Users\\kumchand0\\Apps\\sts-workspace\\tensorflow-java\\model\\b", "serve");
		
		Tensor<Integer> tensor = model.session().runner().fetch("z")
			.feed("x", Tensor.<Integer>create(3, Integer.class))
			.feed("y", Tensor.<Integer>create(3, Integer.class))
			.run().get(0).expect(Integer.class);
		
		System.out.println(tensor.intValue());

	}

}
