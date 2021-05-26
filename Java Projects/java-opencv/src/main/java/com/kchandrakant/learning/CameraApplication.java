package com.kchandrakant.learning;

import java.io.ByteArrayInputStream;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import com.kchandrakant.learning.utility.ImageUtility;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import nu.pattern.OpenCV;

@SuppressWarnings("unused")
public class CameraApplication extends Application {
	private VideoCapture capture;

	public void start(Stage stage) throws Exception {
		OpenCV.loadShared();
		capture = new VideoCapture(0); // The number is the ID of the camera
		ImageView imageView = new ImageView();
		HBox hbox = new HBox(imageView);
		Scene scene = new Scene(hbox);
		stage.setScene(scene);
		stage.show();
		new AnimationTimer() {
			@Override
			public void handle(long l) {
				imageView.setImage(getCaptureWithFaceDetection());
			}
		}.start();
	}
	
	private Image getCapture() {
		Mat mat = new Mat();
		capture.read(mat);
		return ImageUtility.mat2Img(mat);
	}

	private Image getCaptureWithFaceDetection() {
		Mat mat = new Mat();
		capture.read(mat);
		Mat haarClassifiedImg = ImageUtility.detectFace(mat);
		return ImageUtility.mat2Img(haarClassifiedImg);
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}