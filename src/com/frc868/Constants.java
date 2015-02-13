package com.frc868;

import org.opencv.core.Core;

public class Constants {

	public static final String ROBORIO_IP_ADDRESS = "10.8.68.2";
	public static final String CAMERA_FEED_URL = "http://10.8.68.11/mjpg/video.mjpg";
	
	public static final String LIB_FFMPEG = "opencv_ffmpeg2410_64";
	public static final String LIB_OPENCV = Core.NATIVE_LIBRARY_NAME;
	
	public static final String FILE_SAVE_PATH = "C:\\Vision2015\\HSV.txt";
	
	public static final double MAX_POWER_TO_DRIVE = 0.4;
	public static final double POWER_REDUCTION = 0.2;
	
	public static final double OFFSET_FACTOR = 0.2;
	
	public static final double PERCENT_CAMERA = 50;
	
}
