package com.frc868;

import com.frc868.exceptions.CaptureException;
import com.frc868.filters.groups.Filter2015;
import com.frc868.gui.WindowRealTime;
import com.frc868.processors.ToteDetector;

/** 
 * @author Atif Niyaz, Andrew Bass, Calvin Henry
 *
 * Main Program Class
 */
public class ProgramRealTime {
	
	static {
		System.out.println("Loading OpenCV 2.4.10");
		System.loadLibrary(Constants.LIB_OPENCV);
		System.out.println("Loading OpenCV FFMPEG Libs x64_86");
		System.loadLibrary(Constants.LIB_FFMPEG);
	}
	
	public static void main(String [] args) throws CaptureException, Exception {
		
		FileIO.updateConstants(Constants.CONST_PATH);
		FileIO.receiveHSV(Constants.FILE_SAVE_PATH);
		
		Camera camera = new Camera(Constants.CAMERA_FEED_URL, new ToteDetector());
		camera.addFilter(new Filter2015());
		
		Server.setCamera(camera);
		Server.getInstance();
		
		(new Thread() {
			
			public void run() {
				while(true)
					camera.getProcessedFrame();
			}
		}).start();
		
		new WindowRealTime(camera, "TechHOUNDS Vision RunTime 2015");
	}
}
