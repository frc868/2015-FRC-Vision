package com.frc868;

import java.awt.Dimension;
import java.util.ArrayList;

import org.opencv.core.Mat;

import com.frc868.processors.Processor;
import com.frc868.exceptions.CaptureException;
import com.frc868.filters.Filter;
import com.frc868.imgcv.Conversion;

/**
 * Represents a camera feed that can have filters applied.
 */
public class Camera {
	
	private ArrayList<Filter> filters;
	private Dimension resolution;
	private Processor processor;
	
	private String url;
	
	/**
	 * Create Camera feed from url or path, usually from url though
	 */
	public Camera(String url, Processor processor) throws CaptureException {
		this.url = url;
		initalize(processor);
		updateResolution();
	}
	
	/**
	 * Performs extra initialization
	 */
	private void initalize(Processor processor) throws CaptureException {
		this.filters = new ArrayList<Filter>();	
		this.processor = processor;
		this.resolution = new Dimension();
	}
	
	/**
	 * Reads in a frame from an image source and uses it to determine the resolution of the camera, 
	 * assuming each image frame has the same resolution
	 */
	public void updateResolution() throws CaptureException {
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
	public Mat getRawFrame() throws CaptureException {
		
		Mat image;
		
		try {
			image = Conversion.urlToMat(url);
		} catch(Exception e) {
			try {
				image = Conversion.pathToMat(url);
			} catch(Exception e0) {
				e0.printStackTrace();
				throw new CaptureException();
			}
		}
		
		return image;
	}
	
	/**
	 * Get's image frame from camera with filtering applied
	 */
	public Mat getFilteredFrame(Mat image) throws CaptureException {
		Mat filtered = this.applyFilters(image.clone()); 
		return filtered;
	}
	
	/**
	 * Get's image frame from camera with processing applied
	 */
	public Mat getProcessedFrame() throws CaptureException {
		Mat original = getRawFrame();
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