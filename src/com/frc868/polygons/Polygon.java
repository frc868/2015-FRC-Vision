package com.frc868.polygons;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;

public class Polygon {
	public ArrayList<Point> vecs = new ArrayList<Point>();
	
	public Polygon(Point... vecs) {
		for(Point point : vecs) {
			this.vecs.add(point);
		}
	}
	
	public Polygon(List<Point> vecList) {
		for (Point point : vecList) {
			this.vecs.add(point);
		}
	}
	
	/**
	 * I DONT KNOW HOW THIS ALGROTIHTIM WORKS
	 * @param eps
	 * @return
	 */
	public Polygon simplify(double eps) {
		ArrayList<Point> resultList = new ArrayList<Point>();
		Line line = new Line(this.vecs.get(0), this.vecs.get(this.vecs.size() - 1));
		
		double maxDist = Double.NEGATIVE_INFINITY;
		int maxIndex = 0;
		
		for (int i = 2; i < (this.vecs.size() - 1); i++) {
			double dist = line.shortestDistTo(this.vecs.get(i));
			
			if (dist > maxDist) {
				maxDist = dist;
				maxIndex = i;
			}
		}
		
		if (maxDist > eps) {
			Polygon resultOne = new Polygon(this.vecs.subList(0, maxIndex - 1));
			resultOne = resultOne.simplify(eps);
			
			Polygon resultTwo = new Polygon(this.vecs.subList(maxIndex, this.vecs.size() - 1));
			resultTwo = resultTwo.simplify(eps);
			
			resultList = (ArrayList<Point>) resultOne.vecs.subList(0, this.vecs.size() - 2);
			resultList.addAll(resultTwo.vecs.subList(0, this.vecs.size() - 1));
		} else {
			resultList = this.vecs;
		}
		
		return new Polygon(resultList);
	}
	
	public void draw(Mat mat) {
		for (int i = 0; i < vecs.size() - 1; i += 2) {
			Core.line(mat, vecs.get(i), vecs.get(i + 1), new Scalar(0, 0, 255), 3);
		}
	}
}
