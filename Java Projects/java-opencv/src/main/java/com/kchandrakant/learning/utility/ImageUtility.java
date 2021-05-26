package com.kchandrakant.learning.utility;

import java.io.ByteArrayInputStream;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;

import javafx.scene.image.Image;

public class ImageUtility {

	public static Mat detectFace(Mat inputImage) {
		MatOfRect facesDetected = new MatOfRect();
		CascadeClassifier cascadeClassifier = new CascadeClassifier();
		int minFaceSize = Math.round(inputImage.rows() * 0.1f);
		cascadeClassifier.load("./src/main/resources/haarcascade_frontalface_alt.xml");
		cascadeClassifier.detectMultiScale(inputImage, facesDetected, 1.1, 3, Objdetect.CASCADE_SCALE_IMAGE,
				new Size(minFaceSize, minFaceSize), new Size());
		Rect[] facesArray = facesDetected.toArray();
		for (Rect face : facesArray) {
			Imgproc.rectangle(inputImage, face.tl(), face.br(), new Scalar(0, 0, 255), 3);
		}
		return inputImage;
	}

	public static Image mat2Img(Mat mat) {
		MatOfByte bytes = new MatOfByte();
		Imgcodecs.imencode(".jpg", mat, bytes);
		ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes.toArray());
		Image img = new Image(inputStream);
		return img;
	}

}
