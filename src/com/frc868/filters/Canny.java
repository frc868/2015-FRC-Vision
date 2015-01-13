package com.frc868.filters;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/*
 * Uses Canny edge detection to filter an image
 */
public class Canny implements Filter {
	private double lowThreshold, highThreshold;
	
	/*
	 * Default constructor
	 */
	public Canny(){
		this(100, 200);
	}
	
	/*
	 * Takes two threshold values that control that strength of filtering
	 */
	public Canny(double lowThreshold, double highThreshold){
		this.lowThreshold = lowThreshold;
		this.highThreshold = highThreshold;
	}
	
	public Mat apply(Mat source) {
		Mat result = new Mat();
		Imgproc.Canny(source, result, this.lowThreshold, this.highThreshold);
		
		return result;
	}

}