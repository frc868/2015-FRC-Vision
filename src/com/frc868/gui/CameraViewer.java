package com.frc868.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import com.frc868.Camera;
import com.frc868.MatIO;
import com.frc868.processors.Processor;

/**
 * This class filters and renders an image from a camera
 */
public class CameraViewer extends JComponent {
	private Camera camera;
	private Dimension cameraResolution;
	
	/**
	 * Creates an image viewer that pulls frames from a Camera
	 */
	public CameraViewer(Camera camera){
		this.camera = camera;
		this.cameraResolution = camera.getResolution();
		this.setPreferredSize(new Dimension(this.cameraResolution.width, this.cameraResolution.height)); // Resizing space for three images, with the third image centered on bottom row
	}
	
	public void paintComponent(Graphics g){
		
		Mat processedFrame;
		
		try {
			processedFrame = camera.getProcessedFrame();
		} catch(Exception e) {
			processedFrame = new Mat();
		}

		BufferedImage processedImage  = MatIO.toImage(processedFrame);
		g.drawImage(processedImage, 0, 0, null);
	}
	
}
