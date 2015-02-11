package com.frc868.processors;

import org.opencv.core.Mat;

/**
 * @author Andrew Bass
 * 
 * A Processor is a class that does not filter an image, but instead takes a filtered image and 
 * derrives useful information out of it.  It can draw areas of note and other things on the window
 * that controls it, and uses a Server class to send useful data values
 * to a special panel in a Window.
 * 
 * 
 * TODO Instead of passing in the original image, lets pass in a mask Mat that can be overlayed upon any image the user desires
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
