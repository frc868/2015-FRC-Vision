package com.frc868.polygons;

import org.opencv.core.Point;

public class Line {
	public Point beg, end;
	
	public Line(Point beg, Point end) {
		this.beg = beg;
		this.end = end;
	}
	
	public double shortestDistTo(Point point) {
		double numerator = Math.abs( (end.y - beg.y) * beg.x - (end.x - beg.x) * beg.y + end.x * beg.y - end.y * beg.x );
		double demonator = this.dist();
				
		return numerator / demonator;
	}
	
	public double dist() {
		double x = beg.x - end.x;
		x *= x;
		
		double y = beg.y - end.y;
		y *= y;
		
		return Math.sqrt(x + y);
	}
}
