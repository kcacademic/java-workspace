package com.kchandrakant.learning;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import com.kchandrakant.learning.utility.ImageUtility;

import nu.pattern.OpenCV;

public class FileApplication {

	public static void main(String[] args) throws Exception {

		OpenCV.loadShared();
		
		Mat inputImage = Imgcodecs.imread("./src/main/resources/input_image.jpg");
		
		Mat outputImage = ImageUtility.detectFace(inputImage);
		
		Imgcodecs.imwrite("./src/main/resources/output_image.jpg", outputImage);
	}
}