package com.kchandrakant.learning;

import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.Dataset;

public class Main {
  public static void main(String[] args) {
    String logFile = "C:\\Users\\kumchand0\\Apps\\spark-2.3.1-bin-hadoop2.7\\README.md";
    SparkSession spark = SparkSession.builder()
    		.appName("Simple Application")
    		.config("spark.master", "local")
    		.getOrCreate();
    Dataset<String> logData = spark.read().textFile(logFile).cache();

    long numAs = logData.filter(s -> s.contains("a")).count();
    long numBs = logData.filter(s -> s.contains("b")).count();

    System.out.println("Lines with a: " + numAs + ", lines with b: " + numBs);

    spark.stop();
  }
}