package com.frc868.filters;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * Converts a standard BGR image to grayscale
 */
public class GrayScale extends ColorSpace {
	public GrayScale(){
		super(Imgproc.COLOR_BGR2GRAY); // should do a smarter method other than assuming source color space is BGR, but it works for now
	}
}
