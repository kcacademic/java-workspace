package com.sapient.learning.mllib;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.clustering.KMeans;
import org.apache.spark.mllib.clustering.KMeansModel;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;

public class SparkMllibKMeansApp {

	public static void main(String[] args) {
		// Environment Set-up
        System.out.println("KMeans Classification using spark MLlib in Java . . .");
        System.setProperty("hadoop.home.dir", "C:\\Users\\kumchand0\\Apps\\hadoop");
		
        // Configure spark
        SparkConf conf = new SparkConf().setAppName("SparkMllibKMeansApp")
                .setMaster("local[2]")
                .set("spark.executor.memory","3g")
                .set("spark.driver.memory", "3g");
 
        // Start a spark context
        JavaSparkContext jsc = new JavaSparkContext(conf);
        
        // Provide path to data
        String path = "data/sample_kmeans_data.txt";
        JavaRDD<String> data = jsc.textFile(path);
        
        // Parse the loaded data
        JavaRDD<Vector> parsedData = data.map(s -> {
            String[] sarray = s.split(" ");
            double[] values = new double[sarray.length];
            for (int i = 0; i < sarray.length; i++) {
                values[i] = Double.parseDouble(sarray[i]);
            }
            return Vectors.dense(values);
        });
        parsedData.cache();
        
        // Cluster the data into three classes using KMeans
        int numClusters = 3;
        int numIterations = 20;
        KMeansModel model = KMeans.train(parsedData.rdd(), numClusters, numIterations);
        
        int clusterNumber = 0;
        for (Vector center: model.clusterCenters()) {
            System.out.println("Cluster center for Clsuter "+ (clusterNumber++) + " : " + center);
        }
        double cost = model.computeCost(parsedData.rdd());
        System.out.println("Cost: " + cost);
        
        // Evaluate clustering by computing Within Set Sum of Squared Errors
        double WSSSE = model.computeCost(parsedData.rdd());
        System.out.println("Within Set Sum of Squared Errors = " + WSSSE);
        
        // After training, save and load the model to local
        try {
            FileUtils.forceDelete(new File("model\\kmeans"));
            System.out.println("Deleting old model completed.");
        } catch (Exception e) {
        	e.printStackTrace();
        }
        model.save(jsc.sc(), "model\\kmeans");
        System.out.println("Model saved to model\\kmeans");
        KMeansModel sameModel = KMeansModel.load(jsc.sc(), "model\\kmeans");
        
        // Perform prediction on new data
        System.out.println("[9.0, 0.6, 9.0] belongs to cluster "+sameModel.predict(Vectors.dense(9.0, 0.6, 9.0)));
        System.out.println("[0.2, 0.5, 0.4] belongs to cluster "+sameModel.predict(Vectors.dense(0.2, 0.5, 0.4)));
        System.out.println("[2.8, 1.6, 6.0] belongs to cluster "+sameModel.predict(Vectors.dense(2.8, 1.6, 6.0)));
        
        // Stop the spark context
        jsc.stop();
        jsc.close();
	}

}
