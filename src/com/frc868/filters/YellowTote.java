package com.frc868.filters;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 * @author Atif Niyaz
 * 
 * Filters out a range of HSV values which are supposed to resemble
 * the values of the yellow tote.
 */
public class YellowTote implements Filter {

	static Scalar low;
	static Scalar high;
	
	static int 	iLowH = 14,//20;//20;
				iHighH = 26,//40;//40;
				iLowS = 100,//85;
				iHighS = 255,
				iLowV = 69,//50;//140;
				iHighV = 184;//255;
	
	public Mat apply(Mat source) {
		
		if(low == null || high == null) {
			low = new Scalar(iLowH, iLowS, iLowV);
			high = new Scalar(iHighH, iHighS, iHighV);
		}
		
		Mat convertHSV = new Mat();
		Mat threshHOLD = new Mat();
		
		Imgproc.cvtColor(source, convertHSV, Imgproc.COLOR_BGR2HSV);
		Core.inRange(convertHSV, low, high, threshHOLD);
		
		Imgproc.erode(threshHOLD, threshHOLD, Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(5, 5)) );
		Imgproc.dilate(threshHOLD, threshHOLD, Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(5, 5)) ); 

		Imgproc.dilate(threshHOLD, threshHOLD, Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(5, 5)) ); 
		Imgproc.erode(threshHOLD, threshHOLD, Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(5, 5)) );
		  
		return threshHOLD;
	}
	
	public static void defineRange(Scalar low, Scalar high) {
		YellowTote.low = low;
		YellowTote.high = high;
	}
	
	public static Scalar[] getRange() {
		
		if(low == null || high == null) 
			return new Scalar[] {new Scalar(iLowH, iLowS, iLowV), 
					new Scalar(iHighH, iHighS, iHighV)};
		else
			return new Scalar[] {low, high};
	}
}
