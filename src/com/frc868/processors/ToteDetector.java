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
		
	
		/*	Iterate through all contours, each will create a bounding rectangle.
		 *	The biggest rectangle / tote in view will move on in the processing. 
		 */
		for(int i = 0; i < contours.size(); i++) {
			MatOfPoint contour = contours.get(i);
			Rect rect = Imgproc.boundingRect(contour);
			
			if(isValidTote(contour) && rect.size().area() > largestRect.size().area())
				this.largestRect = rect;
		}
		
		/* Draw the Rectangle onto the Mat */
		Core.rectangle(this.targetImage, largestRect.tl(), largestRect.br(), new Scalar(255, 0, 0), 2, 8, 0);

		/* Get the server instance */
		Server s = Server.getInstance();
		
		/* Set some values that will be sent to the server */
		s.setCenter(largestRect.x + largestRect.width / 2.0);
		s.setDistFactor(getDistanceFactor(largestRect));

		/* TODO Delete this hack */
		s.table.putNumber("Polygon Height", largestRect.height);
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
	
	private double getDistanceFactor(Rect largestRect){
		if(isInBottomCorner(largestRect)){
			return -0.001;
		}
		
		double ratio = (double)largestRect.height / 
				(this.camResolution.height * Constants.MAX_POWER_TO_DRIVE / 100); 
		
		ratio = ratio > Constants.MAX_POWER_TO_DRIVE ? Constants.MAX_POWER_TO_DRIVE : ratio - Constants.POWER_REDUCTION;
		return Math.min(Math.max(1 - ratio, 0.0), Constants.MAX_POWER_TO_DRIVE);
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


