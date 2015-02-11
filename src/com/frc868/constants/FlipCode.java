package com.frc868.constants;

/**
 * @author Andrew Bass
 * 
 * Stores constants used by OpenCV to determine how to flip a mat when using the cv::flip function.
 * Used in Mirror filter
 */
public enum FlipCode {
	
	X_AXIS(0),
	Y_AXIS(1),
	BOTH_AXES(-1);

	public int value;
	
	private FlipCode(int value){
		this.value = value;
	}
}