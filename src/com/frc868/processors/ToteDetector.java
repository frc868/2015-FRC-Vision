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
import com.frc868.polygons.Polygon;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * @author Atif Niyaz, Calvin Henry, Andrew Bass
 * 
 * Draws contours around yellow totes and uses a bounding box to determine robot commands, 
 * such as the robot's speed and direction.
 */
public class ToteDetector implements Processor {

	private Rect largestRect;
	private MatOfPoint largestContour;
	private Size camResolution;
	private Mat targetImage;
	
	public Mat process(Mat source, Mat original) {
		
		this.targetImage = original;
		
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
			MatOfPoint contour = contours.get(i);
			Rect rect = Imgproc.boundingRect(contour);
			
			if(isValidTote(contour) && rect.size().area() > largestRect.size().area())
				this.largestRect = rect;
				this.largestContour = contour;
		}
		
		// Draw the Rectangle onto the Mat
		Core.rectangle(this.targetImage, largestRect.tl(), largestRect.br(), new Scalar(255, 0, 0), 2, 8, 0);

		// Instantiate Server
		Server s = Server.getInstance();
		
		// Add Values to Send to Server
		s.setCenter(calculateCenter());
		s.setDistFactor(getDistanceFactor(largestRect));
		
		// TODO Delete this hack
		s.table.putNumber("Polygon Height", largestRect.height);
		
		s.send();
		
		System.out.println(calculateCenter());
		
		return this.targetImage;
	}
	
	private double calculateCenter() {
		
		Rect rect = new Rect();
		rect.height = largestRect.height;
		rect.width = (int) (largestRect.x + largestRect.width - (1.5 * largestRect.height));
		Server.getInstance().setRect(rect);
		return largestRect.x + largestRect.width - (1.5 * largestRect.height/ 2.0);
		// More accurate calculation of center?
	}

	private double getDistanceFactor(Rect largestRect){
		if(isInBottomCorner(largestRect)){
			return 0.0;
		}
		
		double ratio = (double)largestRect.height / (this.camResolution.height * 0.5); 
		
		if (ratio > 0.975) {
			ratio = 1;
		} else {
			ratio -= 0.4;
		}
		
		return Math.min(Math.max(1 - ratio, 0.0), 1.0);
	}
	
	private boolean isValidTote(MatOfPoint contour) {
		Polygon poly = new Polygon(contour.toArray());
		poly = poly.simplify(200);
		poly.draw(this.targetImage);
		
		return true;
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
