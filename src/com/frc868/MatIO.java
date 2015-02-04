package com.frc868;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;

import com.frc868.filters.YellowTote;
import com.frc868.filters.groups.Filter2015;
import com.frc868.processors.ToteDetector;

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
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static void receiveHSV(String fileLoc) throws Exception {
				
		BufferedReader buffer = new BufferedReader(new FileReader(fileLoc));
		
		Scalar low = new Scalar(Double.parseDouble(buffer.readLine().trim()),
				Double.parseDouble(buffer.readLine().trim()),
				Double.parseDouble(buffer.readLine().trim()));
		
		Scalar high = new Scalar(Double.parseDouble(buffer.readLine().trim()),
				Double.parseDouble(buffer.readLine().trim()),
				Double.parseDouble(buffer.readLine().trim()));
		
		YellowTote.defineRange(low, high);
		buffer.close();
	}
}
	