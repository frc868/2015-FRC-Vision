package com.frc868.processors;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.objdetect.CascadeClassifier;

public class CascadeProcessor implements Processor{

	CascadeClassifier c;
	public CascadeProcessor(String path){
		c = new CascadeClassifier(path);
	}
	
	@Override
	public Mat process(Mat source, Mat edit) {
		//Location of totes in image
		MatOfRect detect = new MatOfRect();
				
		//Detect
		c.detectMultiScale(source, detect);
		//Convert to rectangles
		Rect[] rect = detect.toArray();
				
		System.out.println(String.format("Detected " + rect.length + "totes."));
		//Find largest rectangle
		
		Rect large = new Rect(1,1, 1,1);
		for(int i = 0; i < rect.length; i++){
			if(rect[i].size().area() > large.size().area()) large = rect[i];
		}
		Core.rectangle(edit, large.br(), large.tl(), new Scalar(0, 0, 255), 2, 8, 0);
		/*for (Rect rect : detect.toArray()) {
			System.out.println(rect.x + ", " + rect.y + "\t\t" + rect.width + "\t" + rect.height);
		    Core.rectangle(edit, new Point(100,100), new Point(200,200), new Scalar(255, 0, 0), 2, 8, 0);
		}*/
		
		return edit;
	}

}
