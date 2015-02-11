package com.frc868.filters.groups;

import java.util.ArrayList;

import org.opencv.core.Mat;

import com.frc868.filters.Filter;

/**
 * @author Andrew Bass
 * 
 * Represents a group of filters that are applied sequentially
 */
public abstract class FilterGroup implements Filter {
	
	private ArrayList<Filter> filters;
	
	protected FilterGroup(){
		filters = new ArrayList<Filter>();
	}
	
	/**
	 * @param args Add as many Filters as you can. Arguments can be more than one
	 */
	protected void add(Filter... args){
		for (Filter filter : args){
			filters.add(filter);
		}
	}
	
	/**
	 * @param source Applys the filter to the image
	 * @return the modified image
	 */
	public Mat apply(Mat source){
		for (Filter filter : filters){
			source = filter.apply(source);
		}
		
		return source;
	}
}
