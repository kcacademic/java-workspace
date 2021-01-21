package com.sapient.learning.mllib;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.classification.LogisticRegressionModel;
import org.apache.spark.mllib.classification.LogisticRegressionWithLBFGS;
import org.apache.spark.mllib.evaluation.MulticlassMetrics;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.util.MLUtils;

import scala.Tuple2;

public class SparkMllibLogisticRegressionApp {

	public static void main(String[] args) {
		// Environment Set-up
        System.out.println("Logistic Regression using spark MLlib in Java . . .");
        System.setProperty("hadoop.home.dir", "C:\\Users\\kumchand0\\Apps\\hadoop");

		// Configure spark
		SparkConf conf = new SparkConf().setAppName("SparkMllibLogisticRegressionApp")
				.setMaster("local[2]")
                .set("spark.executor.memory","3g")
                .set("spark.driver.memory", "3g");

		// Start a spark context
		SparkContext sc = new SparkContext(conf);
		
		// Provide path to data transformed as [feature vectors]
		String path = "data/sample_libsvm_data.txt";
		JavaRDD<LabeledPoint> data = MLUtils.loadLibSVMFile(sc, path).toJavaRDD();

		// Split initial RDD into two... [80% training data, 20% testing data].
		JavaRDD<LabeledPoint>[] splits = data.randomSplit(new double[] { 0.8, 0.2 }, 11L);
		JavaRDD<LabeledPoint> training = splits[0].cache();
		JavaRDD<LabeledPoint> test = splits[1];
		
		// Run training algorithm to build the model
		LogisticRegressionModel model = new LogisticRegressionWithLBFGS().setNumClasses(10).run(training.rdd());

		// Compute raw scores on the test set
		JavaPairRDD<Object, Object> predictionAndLabels = test
				.mapToPair(p -> new Tuple2<>(model.predict(p.features()), p.label()));
		
		// Get evaluation metrics
		MulticlassMetrics metrics = new MulticlassMetrics(predictionAndLabels.rdd());
		double accuracy = metrics.accuracy();
		System.out.println("Accuracy = " + accuracy);
		
		// After training, save and load the model to local
        try {
            FileUtils.forceDelete(new File("model\\logistic-regression"));
            System.out.println("Deleting old model completed.");
        } catch (Exception e) {
        	e.printStackTrace();
        }
		model.save(sc, "model\\logistic-regression");
		System.out.println("Model saved to model\\logistic-regression");
		@SuppressWarnings("unused")
		LogisticRegressionModel sameModel = LogisticRegressionModel.load(sc, "model\\logistic-regression");
		
		// Perform prediction on new data
		
		// Stop the spark context
		sc.stop();

	}

}
