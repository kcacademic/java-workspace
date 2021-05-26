package com.kchandrakant.learning.mllib;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.regression.LinearRegressionModel;
import org.apache.spark.mllib.regression.LinearRegressionWithSGD;
import org.apache.spark.mllib.util.MLUtils;

import scala.Tuple2;

public class SparkMllibLinearRegressionApp {

	public static void main(String[] args) {
		// Environment set-up
		System.out.println("Linear Regression using spark MLlib in Java . . .");
		System.setProperty("hadoop.home.dir", "C:\\Users\\kumchand0\\Apps\\hadoop");

		// Configure Spark
		SparkConf conf = new SparkConf().setAppName("SparkMllibLinearRegressionApp").setMaster("local[2]")
				.set("spark.executor.memory", "3g").set("spark.driver.memory", "3g");

		// Start a Spark Context
		SparkContext sc = new SparkContext(conf);

		// Provide path to data
		String path = "data/sample_linear_regression_data.txt";
		JavaRDD<LabeledPoint> data = MLUtils.loadLibSVMFile(sc, path).toJavaRDD();
		
		System.out.println(data.takeSample(false, 1).get(0).features());
		System.out.println(data.takeSample(false, 1).get(0).label());

		// Split initial data into two... [80% training data, 20% testing data]
		JavaRDD<LabeledPoint>[] splits = data.randomSplit(new double[] { 0.8, 0.2 }, 11L);
		JavaRDD<LabeledPoint> training = splits[0].cache();
		JavaRDD<LabeledPoint> test = splits[1];

		// Run training algorithm to build the model
		int numIterations = 100;
		double stepSize = 0.00000001;
		LinearRegressionModel model = LinearRegressionWithSGD.train(JavaRDD.toRDD(training), numIterations, stepSize);

		// Compute raw scores on the test data
		JavaPairRDD<Double, Double> valuesAndPreds = test
				.mapToPair(point -> new Tuple2<>(model.predict(point.features()), point.label()));

		// Get evaluation metrics
		double error = valuesAndPreds.mapToDouble(pair -> {
			double diff = pair._1() - pair._2();
			return diff * diff;
		}).mean();
		System.out.println("Test Mean Squared Error = " + error);

		// Save the model to local and load back
        try {
            FileUtils.forceDelete(new File("model\\linear-regression"));
            System.out.println("Deleting old model completed.");
        } catch (Exception e) {
        	e.printStackTrace();
        }
		model.save(sc, "model\\linear-regression");
		System.out.println("Model saved to model\\logistic-regression");
		@SuppressWarnings("unused")
		LinearRegressionModel sameModel = LinearRegressionModel.load(sc, "model\\linear-regression");
		
		// Perform prediction on new data
		
		// Stop the Spark Context
		sc.stop();

	}

}
