package com.frc868;

import org.opencv.core.Core;

public class Constants {

	public static final String CONST_PATH = "C:\\Vision2015\\constants.ini";

	public static String ROBORIO_IP_ADDRESS = "10.8.68.2";
	public static String CAMERA_FEED_URL = "http://10.8.68.11/mjpg/video.mjpg";
	
	public static String LIB_FFMPEG = "opencv_ffmpeg2410_64";
	public static String LIB_OPENCV = Core.NATIVE_LIBRARY_NAME;
	
	public static String FILE_SAVE_PATH = "C:\\Vision2015\\color_range.ini";
	
	public static double MAX_POWER_TO_DRIVE = 0.3;
	public static double POWER_REDUCTION = 0.2;
	
	public static double OFFSET_FACTOR = 0.1;
	
	public static double PERCENT_CAMERA = 65;//57.5;
	
}
