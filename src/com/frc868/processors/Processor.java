package com.frc868.processors;

import org.opencv.core.Mat;

/**
 * @author Andrew Bass
 * 
 * A Processor is a class that does not filter an image, but instead takes a filtered image and 
 * derrives useful information out of it.  It can draw areas of note and other things on the window
 * that controls it, and uses a [INSERT PROCESSOR INTERFACE HERE] class to send useful data values
 * to a special panel in a Window.
 * 
 * It also is given a [INSERT ROBOT COMMUNICATION CLASS HERE] to send data to the robot
 */
public interface Processor {

	/**
	 * @param source The source image file
	 * @param original The original image file non-modified
	 * 
	 * @return The processed Mat file
	 */
	public Mat process(Mat source, Mat original);	
}
