package com.frc868.gui;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

import com.frc868.Camera;
import com.frc868.FileIO;

/**
 * @author Andrew Bass
 * 
 * This class filters and renders an image from a camera
 */
@SuppressWarnings("serial")
public class CameraViewer extends JComponent {
	
	private Camera camera;
	private Dimension cameraResolution;
	
	/**
	 * Creates an image viewer that pulls frames from a Camera
	 */
	public CameraViewer(Camera camera) {
		
		this.camera = camera;
		this.cameraResolution = camera.getResolution();
		this.setPreferredSize(new Dimension(this.cameraResolution.width, this.cameraResolution.height)); // Resizing space for three images, with the third image centered on bottom row
	}
	
	public void paintComponent(Graphics g){
		g.drawImage(FileIO.toImage(camera.getProcessedFrame()), 0, 0, null);
	}
	
}
