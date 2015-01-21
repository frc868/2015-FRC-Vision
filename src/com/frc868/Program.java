package com.frc868;
import org.opencv.core.Core;

import com.frc868.exceptions.CaptureException;
import com.frc868.filters.groups.Filter2015;
import com.frc868.gui.Window;
import com.frc868.processors.ToteDetector;

/** 
 * @author Atif Niyaz, Andrew Bass, Calvin Henry
 *
 * Main Program Class
 */
public class Program {
	
	static {
		System.out.println("Loading OpenCV 2.4.10");
		System.loadLibrary("libs/" + Core.NATIVE_LIBRARY_NAME);
		System.out.println("Loading OpenCV FFMPEG Libs x64_86");
		System.loadLibrary("libs/opencv_ffmpeg2410_64");
	}
	
	public static void main(String [] args) throws CaptureException {
		
		String url = "http://10.8.68.11/mjpg/video.mjpg";
		
		Camera camera = new Camera(url, new ToteDetector());
		camera.addFilter(new Filter2015());
		
		Server.setCamera(camera);
		Server server = Server.getInstance();
		server.setSpeed(0.8);

		Window window = new Window("Vision Viewer 2015", camera, "C:/Vision2015/VISION_FILTER_PROS");
	}

}
