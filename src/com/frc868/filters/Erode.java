package com.frc868.filters;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 * @author Andrew Bass
 * 
 * Expands dark areas within a Mat
 */
public class Erode implements Filter {

	private int shapeCode;
	private double erosionAmount;
	
	public Erode(int shapeCode, double erosionAmount){
		this.shapeCode = shapeCode;
		this.erosionAmount = erosionAmount;
	}
	
	public Mat apply(Mat source) {
		Mat result = new Mat();
		
		Imgproc.erode(source, result, Imgproc.getStructuringElement(shapeCode, new Size(erosionAmount, erosionAmount)));
		
		return result;
	}

}
