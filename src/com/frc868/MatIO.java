package com.frc868;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;

/**
 * @author Andrew Bass
 * 
 * Static class that contains useful functions for outputing and saving Matrices
 */
public class MatIO {
	
	/**
	 * Converts a Mat containing color values to a BufferedImage that can be drawn or saved
	 */
	public static BufferedImage toImage(Mat source){
		
		MatOfByte bytemat = new MatOfByte();

		Highgui.imencode(".jpg", source, bytemat);
		byte[] bytes = bytemat.toArray();

		InputStream in = new ByteArrayInputStream(bytes);

		try {
			return ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
	