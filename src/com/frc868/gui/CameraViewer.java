package com.frc868.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import com.frc868.Camera;
import com.frc868.MatIO;

/**
 * @author Andrew Bass
 * 
 * This class filters and renders an image from a camera
 */
@SuppressWarnings("serial")
public class CameraViewer extends JComponent {
	
	private Camera camera;
	private Dimension cameraResolution;
	private double scale;
	
	/**
	 * Creates an image viewer that pulls frames from a Camera
	 */
	public CameraViewer(Camera camera, double initialScale) {
		this.scale = initialScale;
		this.camera = camera;
		this.cameraResolution = camera.getResolution();
		this.setPreferredSize(new Dimension(this.cameraResolution.width, this.cameraResolution.height)); // Resizing space for three images, with the third image centered on bottom row
	}
	
	public void setScale(double scale) {
		this.scale = scale;
		
		int width = (int) (cameraResolution.width * scale);
		int height = (int) (cameraResolution.height * scale);
		
		this.setPreferredSize(new Dimension(width, height));
	}
	
	public void paintComponent(Graphics g){
		BufferedImage image = MatIO.toImage(camera.getProcessedFrame());

		int width = (int) (cameraResolution.width * scale);
		int height = (int) (cameraResolution.height * scale);
		
		Image scaled = image.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
		
		g.drawImage(scaled, 0, 0, null);
	}
	
}
