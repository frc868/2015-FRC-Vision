package com.frc868.processors;

import java.awt.Graphics;

import org.opencv.core.Mat;

/**
 * A Processor that returns any Mat sent to it without derriving any data.
 */
public class NoProcessing implements Processor {
	public Mat process(Mat source, Mat original){
		return original;
	}
}