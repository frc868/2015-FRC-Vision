package com.frc868.processors;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import com.frc868.Constants;
import com.frc868.Server;
import com.frc868.polygons.Polygon;

/**
 * @author Atif Niyaz, Calvin Henry, Andrew Bass
 * 
 * Draws contours around yellow totes and uses a bounding box to determine robot commands, 
 * such as the robot's speed and direction.
 */
public class ToteDetector implements Processor {

	private Rect largestRect;
	private Rect secondLargestRect;
	
	private Size camResolution;
	private Mat targetImage;
	
	public Mat process(Mat source, Mat original) {
		
		this.targetImage = original;
		
		/* Get the resolution of the camera feed*/
		camResolution = source.size();
		
		/* Find the contours of the tote */
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Imgproc.findContours(source, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
		
		/* Set Largest Rect to be 0 x 0 so that it can compare*/
		largestRect = new Rect(0, 0, 0, 0);
		secondLargestRect = new Rect(0, 0, 0, 0);
	
		/*	Iterate through all contours, each will create a bounding rectangle.
		 *	The biggest rectangle / tote in view will move on in the processing. 
		 */
		for(int i = 0; i < contours.size(); i++) {
			MatOfPoint contour = contours.get(i);
			Rect rect = Imgproc.boundingRect(contour);
			
			if(rect.size().area() > secondLargestRect.size().area())
				if(rect.size().area() > largestRect.size().area())
					largestRect = rect;
				else
					secondLargestRect = rect;
		}
		
		largestRect = determineValidRect(largestRect, secondLargestRect);

		/* Get the server instance */
		Server s = Server.getInstance();
		
		if(largestRect != null) {
			Core.rectangle(this.targetImage, largestRect.tl(), largestRect.br(), new Scalar(255, 0, 255), 2, 8, 0);
			s.setCenter(largestRect.x + largestRect.width / 2.0);
			s.table.putNumber("Polygon Height", largestRect.height);
		} else {
			s.setCenter(0);
			s.table.putNumber("Polygon Height", 0);
		}
		
		s.setDistFactor(getDistanceFactor(largestRect));
		s.send();
		
		return this.targetImage;
	}
	
//	private double calculateCenter() {
//		
//		Rect rect = new Rect();
//		rect.height = largestRect.height;
//		rect.width = (int) (largestRect.x + largestRect.width - (1.5 * largestRect.height));
//		Server.getInstance().setRect(rect);
//		return largestRect.x + largestRect.width - (1.5 * largestRect.height/ 2.0);
//		// More accurate calculation of center?
//	}
	
	private Rect determineValidRect(Rect largestRect, Rect secondLargestRect) {
		if(largestRect.x + largestRect.width / 2.0 < camResolution.width / 4.0 ||
				largestRect.y + largestRect.height / 2.0 < camResolution.height / 3.0) {
			if(secondLargestRect.x + secondLargestRect.width / 2.0 < camResolution.width / 4.0 ||
					secondLargestRect.y + secondLargestRect.height / 2.0 < camResolution.height / 3.0) {
				return null;
			} else {
				return secondLargestRect;
			}
		} else
			return largestRect;
	}
	
	private double getDistanceFactor(Rect largestRect){
		
		if(largestRect == null || largestRect.x == 0 || largestRect.y == 0)
			return 0.0;
		
		double ratio = (double)largestRect.height / 
				(this.camResolution.height * Constants.PERCENT_CAMERA / 100); 
		
		System.out.println("Unadjusted Ratio: " + (1-ratio));
//		
//		ratio = Math.min(Math.max(1 - ratio, 0.0), Constants.MAX_POWER_TO_DRIVE);
//		ratio = ratio > Constants.MAX_POWER_TO_DRIVE ? Constants.MAX_POWER_TO_DRIVE : 
//			(ratio > 0 ? ratio : 0.0);
//		

		ratio = (1-ratio) > Constants.MAX_POWER_TO_DRIVE ? Constants.MAX_POWER_TO_DRIVE : 
			(1-ratio > .05 ? 1-ratio : 0);
		
		System.out.println("Adjusted Ratio " + ratio);
		
		return Math.min(Math.max(ratio, 0.0), Constants.MAX_POWER_TO_DRIVE);
//		return ratio;
	}
	
	private boolean isValidTote(MatOfPoint contour) {
		Polygon poly = new Polygon(contour.toArray());
		poly = poly.simplify(200);
		poly.draw(this.targetImage);
		
		return true;
	}
	
	private boolean isInBottomCorner(Rect largestRect){
		final double eps = 10;
		
		double distFromBottomRight = camResolution.width - (largestRect.x + largestRect.width);
		double distFromBottomLeft = largestRect.x;	
		
		return distFromBottomRight < eps || distFromBottomLeft < eps;
	}
	
//	private class RectComparator implements Comparator<Rect> {
//
//		public int compare(Rect arg0, Rect arg1) {
//			
//			if(arg0.size().height > arg1.size().height)
//				return 1;
//			else if(arg0.size().height < arg1.size().height)
//				return -1;
//			else 
//				return 0;
//		}
//		
//	}
}


