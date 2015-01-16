package com.frc868;
import javax.swing.Timer;

import org.opencv.core.Core;

import com.frc868.exceptions.CaptureException;
import com.frc868.filters.groups.Filter2015;
import com.frc868.gui.SliderWindow;
import com.frc868.gui.Window;
import com.frc868.processors.ToteDetector;

public class Program {
	
	public static int counter;
	public static long time;
	
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	public static void main(String [] args) throws CaptureException {
		
		counter = 0;
		time = System.currentTimeMillis();
		
		//Camera camera = new Camera("http://10.8.68.11/mjpg/video.mjpg", new ToteDetector());
		Camera camera = new Camera(0, new ToteDetector());
		camera.addFilter(new Filter2015());
		
		Server.setCamera(camera);
		Server.getInstance();

		Window window = new Window("Processed Image", camera, "C:/Vision2015/VISION_FILTER_PROS");
	}

}
