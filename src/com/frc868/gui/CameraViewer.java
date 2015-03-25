package com.frc868.gui;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import com.frc868.Camera;
import com.frc868.FileIO;
import com.frc868.Program;

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
		FileIO.countCall++;
		
		Mat image = camera.getProcessedFrame();
		g.drawImage(FileIO.toImage(image), 0, 0, null);
		
		if(FileIO.countCall % 3 == 0)
			Highgui.imwrite(Program.folderName + "\\img_" + (FileIO.countCall / 3) + ".png", image);
	}
	
}
