package com.frc868.polygons;

import org.opencv.core.Point;

public class PointUtil {
	public static Point add(Point one, Point two) {
		return new Point(one.x + two.x, one.y + two.y);
	}
	
	public static Point sub(Point one, Point two) {
		return new Point(one.x - two.x, one.y - two.y);
	}
	
	public static Point mul(Point one, Point two) {
		return new Point(one.x * two.x, one.y * two.y);
	}

}
