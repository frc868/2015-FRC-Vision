package com.frc868.filters;

import org.opencv.imgproc.Imgproc;

/**
 * @author Andrew Bass
 * 
 * Converts a standard BGR image to GrayScale
 */
public class GrayScale extends ColorSpace {
	public GrayScale(){
		super(Imgproc.COLOR_BGR2GRAY); // should do a smarter method other than assuming source color space is BGR, but it works for now
	}
}
