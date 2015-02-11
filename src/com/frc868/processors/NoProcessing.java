package com.frc868.processors;

import org.opencv.core.Mat;

/**
 * @author Andrew Bass
 * 
 * This processor does not modify the original Mat sent to it. It will
 * return what was given.
 */
public class NoProcessing implements Processor {
	
	/**
	 * @param source it doesn't matter
	 * @param original the original image
	 * @return the original image
	 */
	public Mat process(Mat source, Mat original){
		return original;
	}
}