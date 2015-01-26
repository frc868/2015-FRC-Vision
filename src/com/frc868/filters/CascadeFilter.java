package com.frc868.filters;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.objdetect.CascadeClassifier;

public class CascadeFilter implements Filter{

	CascadeClassifier c;
	
	/**
	 * 
	 * @param path The path to the cascade.xml
	 */
	public CascadeFilter(String path){
		this.c = new CascadeClassifier(path);
	}
	
	
	public Mat apply(Mat source) {
		//Location of totes in image
		MatOfRect output = new MatOfRect();
		//Edited image
		Mat rtrn = new Mat();
		//Detect
		c.detectMultiScale(source, output);
		
		Rect[] rect = output.toArray();
		
		for (Rect r : rect) {
			Core.rectangle(rtrn, r.br(), r.tl(), new Scalar(0,0,255));
		}
		
		return rtrn;
	}

}
