package com.frc868.filters;

import org.opencv.core.Mat;

/**
 * @author Andrew Bass
 * 
 * Interface that represents a series of steps used to modify and change an image
 */
public interface Filter {
	
	/**
	 * Applies filter to image
	 */
	public Mat apply(Mat source);
	
}
