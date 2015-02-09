package com.frc868;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

import com.frc868.exceptions.CaptureException;
import com.frc868.filters.Filter;
import com.frc868.processors.NoProcessing;
import com.frc868.processors.Processor;
/**
 * @author Andrew Bass, Atif Niyaz, Calvin Henry
 * 
 * Represents a camera feed that can have filters applied.
 */
public class Camera {
	
	private long initTime;
	
	private VideoCapture capture;
	private ArrayList<Filter> filters;
	private Dimension resolution;
	private Processor processor;
	
	/**
	 * By default, uses empty Processor
	 */
	public Camera() throws CaptureException {
		this(0, new NoProcessing());
	}
	
	/**
	 * By default, Camera opens feed from default video camera
	 */
	public Camera(Processor processor) throws CaptureException {
		this(0, processor);
	}
	
	/**
	 * Use webcam id
	 */
	public Camera(int id, Processor processor) throws CaptureException {
		capture = new VideoCapture(id);
		initalize(processor);
		updateResolution();
	}
	
	/**
	 * Create Camera feed from url or path, usually from url though
	 */
	public Camera(String url, Processor processor) throws CaptureException {
		capture = new VideoCapture(url);
		initalize(processor);
		updateResolution();
	}
	
	/**
	 * Performs extra initialization
	 */
	private void initalize(Processor processor) throws CaptureException {
		
		if(!capture.isOpened()) {
			System.out.println("Could not open specified filepath / url, defaulting to first connected hardware camera.");
			capture.open(0);//System.exit(-1);
		}
		
		this.filters = new ArrayList<Filter>();	
		this.processor = processor;
		this.resolution = new Dimension();
	}
	
	/**
	 * Reads in a frame from an image source and uses it to determine the resolution of the camera, 
	 * assuming each image frame has the same resolution
	 */
	public void updateResolution(){
		Mat mat = this.getRawFrame();
		
		this.resolution = new Dimension(mat.cols(), mat.rows());
	}
	
	/**
	 * Get's camera's resolution
	 */
	public Dimension getResolution() { return this.resolution; }
	
	/**
	 * Get's an unprocessed image frame before filtering and processing
	 */
	public Mat getRawFrame(){
		
		Server.setCamera(this);
		
		if(!capture.grab() || Server.getInstance().getIsTeleop()) { // Stream is broken of EOF or we are in Teleop
			System.out.println("Stream is broken or we are at EOF or we are at 15 second time limit");
			System.exit(99);
		}
		
		Mat image = new Mat();
		capture.read(image);
		
		return image;
	}
	
	/**
	 * Get's image frame from camera with filtering applied
	 */
	public Mat getFilteredFrame(Mat image){
		
		if(image == null)
			image = this.getRawFrame();
		Mat filtered = this.applyFilters(image.clone()); 
		
		return filtered;
	}
	
	/**
	 * Get's image frame from camera with processing applied
	 */
	public Mat getProcessedFrame(){
		Mat original = this.getRawFrame();
		Mat filtered = this.getFilteredFrame(original);
		
		return this.processor.process(filtered, original);
	}
	
	/**
	 * Returns camera's processor
	 */
	public Processor getProcessor(){
		return this.processor;
	}
	
	/**
	 * Add a filter that will be applied when image is read from capture source
	 */
	 public void addFilter(Filter... filterArgs){
		 for (Filter filter : filterArgs){
			 this.filters.add(filter);
		 }
	 }
	 
	 /**
	  * Apply filters as they are stored within the ArrayList
	  */
	 private Mat applyFilters(Mat image){
		 
		 for (Filter filter: filters){
			image = filter.apply(image);
		 } 
		 return image;
	 }
}