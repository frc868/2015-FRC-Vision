package com.frc868.processors;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import com.frc868.Program;

/**
 * Draws contours around totes and uses a bounding box to determine robot commands, such as the robots speed and direction
 *
 */
public class ToteDetector implements Processor {

	public Rect largestRect;
	
	public Mat process(Mat source, Mat original) {
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Mat hierarchy = new Mat();
		Imgproc.findContours(source, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
		
		Mat result = Mat.zeros(source.size(), source.type());
		Imgproc.drawContours(result, contours, -1, new Scalar(255, 255, 255));

		List<Rect> totes = new ArrayList<>();
		
		largestRect = new Rect(0, 0, 0, 0);
		
		for(int i = 0; i < contours.size(); i++) {
			totes.add(Imgproc.boundingRect(contours.get(i)));
			
			if(totes.get(i).size().area() > largestRect.size().area())
				largestRect = totes.get(i);
		}
		
		Collections.sort(totes, new Comparator<Rect>() {

			@Override
			public int compare(Rect arg0, Rect arg1) {
				
				if(arg0.size().height > arg1.size().height)
					return 1;
				else if(arg0.size().height < arg1.size().height)
					return -1;
				else 
					return 0;
			}
			
		});
		
		Core.rectangle(original, largestRect.tl(), largestRect.br(), new Scalar(255, 0, 0), 2, 8, 0);
		System.out.println("Distance: " + (largestRect.height * 0.1018867924528208 / 12.0));
		
		Program.counter++;
		long sec = (System.currentTimeMillis() - Program.time)/1000;
		
		System.out.println((double) Program.counter/(double) sec);
		
		return original;
	}
}
