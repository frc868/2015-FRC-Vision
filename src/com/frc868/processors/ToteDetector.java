package com.frc868.processors;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import com.frc868.Server;

/**
 * @author Atif Niyaz, Calvin Henry, Andrew Bass
 * 
 * Draws contours around yellow totes and uses a bounding box to determine robot commands, 
 * such as the robot's speed and direction.
 */
public class ToteDetector implements Processor {

	private Rect largestRect;
	private Size camResolution;
	
	public Mat process(Mat source, Mat original) {
		
		// Get Resolution of Video Feed
		camResolution = source.size();
		
		// Find Contours of Tote
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Imgproc.findContours(source, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
		
		// Set Largest Rect to be 0 x 0
		largestRect = new Rect(0, 0, 0, 0);
		
		// Iterate through all contours, each will create a bounding rectangle.
		// The biggest rectangle / tote in view will move on in the processing.
		for(int i = 0; i < contours.size(); i++) {
			
			Rect rect = Imgproc.boundingRect(contours.get(i));
			
			if(rect.size().area() > largestRect.size().area())
				largestRect = rect;
		}
		
		// Draw the Rectangle onto the Mat
		Core.rectangle(original, largestRect.tl(), largestRect.br(), new Scalar(255, 0, 0), 2, 8, 0);

		// Instantiate Server
		Server s = Server.getInstance();
		
		// Add Values to Send to Server
		s.setCenter(largestRect.x + largestRect.width / 2);
		s.setDistFactor(getDistanceFactor(largestRect));
		s.send();
		
		return original;
	}
	
	private double getDistanceFactor(Rect largestRect){
		if(isInBottomCorner(largestRect)){
			return 0.0;
		}
		
		double ratio = (double)largestRect.height / (this.camResolution.height * 0.30); 
		System.out.println("DHAHHA; " + ratio);
		
		if (ratio > 0.95) {
			ratio = 1;
		} else {
			ratio -= 0.75;
		}
		
		return Math.min(Math.max(1 - ratio, 0.0), 1.0);
	}

	/*
	private boolean isChangeTooGreat(Rect largestRect){
		return Math.abs(lastLargestRect.height - largestRect.height) > 140 ;
	}*/
	
	private boolean isInBottomCorner(Rect largestRect){
		final double eps = 10;
		
		double distFromBottomRight = camResolution.width - (largestRect.x + largestRect.width);
		double distFromBottomLeft = largestRect.x;	
		
		if (distFromBottomRight < eps || distFromBottomLeft < eps) {
			return true;
		}
		
		return false;
	}
}

class RectComparator implements Comparator<Rect> {

	public int compare(Rect arg0, Rect arg1) {
		
		if(arg0.size().height > arg1.size().height)
			return 1;
		else if(arg0.size().height < arg1.size().height)
			return -1;
		else 
			return 0;
	}
	
}
