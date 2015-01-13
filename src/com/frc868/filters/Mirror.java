package com.frc868.filters;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import com.frc868.constants.FlipCode;

/**
 * Horizontially and / or vertically mirrors an image, perfect for front facing webcams
 */
public class Mirror implements Filter {
	private int flipCode;
	
	/*
	 * Can mirror over either the x, y, or both axes
	 */
	public Mirror(FlipCode code){
		this.flipCode = code.value;
	}
	
	public Mat apply(Mat source){
		Mat result = new Mat();
		
		Core.flip(source, result, flipCode);
		return result;
	}
}
