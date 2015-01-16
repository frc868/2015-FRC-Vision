package com.frc868.processors;

import java.util.ArrayList;
import java.util.Collections;
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
 * Draws contours around totes and uses a bounding box to determine robot commands, such as the robots speed and direction
 *
 */
public class ToteDetector implements Processor {

	public Rect largestRect;
	public Size resolution;
	public Rect lastLargestRect;
	
	public Mat process(Mat source, Mat original) {
		resolution = source.size();
		
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Mat hierarchy = new Mat();
		Imgproc.findContours(source, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
		
		Mat result = Mat.zeros(source.size(), source.type());
		Imgproc.drawContours(result, contours, -1, new Scalar(255, 255, 255));

		List<Rect> totes = new ArrayList<Rect>();
		
		largestRect = new Rect(0, 0, 0, 0);
		
		for(int i = 0; i < contours.size(); i++) {
			totes.add(Imgproc.boundingRect(contours.get(i)));
			
			if(totes.get(i).size().area() > largestRect.size().area())
				largestRect = totes.get(i);
		}
		
		Collections.sort(totes, new RectComparator());
			
		Core.rectangle(original, largestRect.tl(), largestRect.br(), new Scalar(255, 0, 0), 2, 8, 0);

		Server s = Server.getInstance();
		s.setCenter(largestRect.x + largestRect.width / 2);
		
		// Calculating distance factor
		double distFactor = getDistanceFactor(largestRect);
		s.setDistFactor(distFactor);
		
		s.sendToSmartDashboard();
		return original;
	}
	
	private double getDistanceFactor(Rect largestRect){
		if(isInBottomCorner(largestRect)){
			return 0.0;
		}
		
		double ratio = (double)largestRect.height / (this.resolution.height * 0.6); 
		
		return Math.min(Math.max(1 - ratio, 0.0), 1.0);
	}
	
	private boolean isInBottomCorner(Rect largestRect){
		final double eps = 10;
		
		double distFromBottomRight = resolution.width - (largestRect.x + largestRect.width);
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
