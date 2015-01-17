package com.frc868.filters;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * @author Andrew Bass
 * 
 * Converts from one colorspace to another
 */
public class ColorSpace implements Filter {
	private int colorCode;
	
	public ColorSpace(int colorCode){
		this.colorCode = colorCode;
	}
	
	public Mat apply(Mat source){
		Mat result = new Mat();
		
		Imgproc.cvtColor(source, result, colorCode);
		
		return result;
	}
}
