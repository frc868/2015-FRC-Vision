package com.frc868.gui;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.frc868.Camera;

/** 
 * @author Atif Niyaz, Calvin Henry, Andrew Bass
 * 
 * Creates a new Window with the Camera
 */
@SuppressWarnings("serial")
public class Window extends JFrame {
	
	private CameraViewer viewer;
	
	private Timer timer;
	private JPanel sliders;
	
	private Container pane;
	
	/**
	 * Creates a Window with a specified Camera
	 */
	public Window(Camera camera, String title){
		
		this.viewer = new CameraViewer(camera);
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
	}
}
