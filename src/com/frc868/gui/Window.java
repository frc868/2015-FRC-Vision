package com.frc868.gui;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.frc868.Camera;
import com.frc868.MatIO;

/** 
 * @author Atif Niyaz, Calvin Henry, Andrew Bass
 * 
 * Creates a new Window with the Camera
 */
@SuppressWarnings("serial")
public class Window extends JFrame implements ActionListener {
	
	private Camera camera;
	private CameraViewer viewer;
	
	private Timer timer;
	private JButton save;
	private JPanel sliders;
	
	private int counter = 0;
	
	private String path;
	private Container pane;
	
	/**
	 * Creates a Window with a specified Camera
	 */
	public Window(String title, Camera camera, String path){
		
		this.camera = camera;
		
		this.viewer = new CameraViewer(camera, 1);
		this.timer = new Timer(0, (ActionListener) this);
		this.sliders = new SliderView(viewer);
		
		pane = this.getContentPane();
		
		FlowLayout flow = new FlowLayout();
		flow.setAlignment(FlowLayout.LEADING);
		
		pane.setLayout(flow);
		pane.add(viewer);
		pane.add(sliders);
		
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(title);
		
		this.pack();
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
			
		timer.start();
		this.path = path;
	}

	/**
	 * This method is called as fast as possible by the timer and initiates repainting 
	 */
	public void actionPerformed(ActionEvent actionEvent) {
		if(actionEvent.getSource() == save){
			
			BufferedImage img;
			
			try {
				img = MatIO.toImage(camera.getProcessedFrame());
			} catch(Exception e) {
				e.printStackTrace();
				return;
			}
			
			try {
				
				File output = new File(path + Integer.toString(counter) + ".png");
				
				while(output.exists()) {
					counter++;
					output = new File(path + Integer.toString(counter) + ".png");
				}
				
				ImageIO.write(img, "png", output);
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		this.revalidate();
		this.repaint();
		this.pack();
	}
}
