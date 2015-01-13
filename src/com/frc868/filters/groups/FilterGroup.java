package com.frc868.filters.groups;

import java.util.ArrayList;

import org.opencv.core.Mat;

import com.frc868.filters.Filter;

/**
 * Represents a group of filters that are applied sequentially
 */
public abstract class FilterGroup implements Filter {
	private ArrayList<Filter> filters;
	
	protected FilterGroup(){
		filters = new ArrayList<Filter>();
	}
	
	protected void add(Filter... args){
		for (Filter filter : args){
			filters.add(filter);
		}
	}
	
	public Mat apply(Mat source){
		for (Filter filter : filters){
			source = filter.apply(source);
		}
		return source;
	}
}
