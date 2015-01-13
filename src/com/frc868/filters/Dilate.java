package com.frc868.filters;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 * Expands bright areas within an image, the inverse of an erosion on an image
 */
public class Dilate implements Filter {
	
	private int shapeCode;
	private double dilateAmount;
	
	public Dilate(int shapeCode, double dilateAmount){
		this.shapeCode = shapeCode;
		this.dilateAmount = dilateAmount;
	}

	public Mat apply(Mat source) {
		Mat result = new Mat();
		
		Imgproc.dilate(source, result, Imgproc.getStructuringElement(shapeCode, new Size(this.dilateAmount, this.dilateAmount)));
		
		return result;
	}

}
