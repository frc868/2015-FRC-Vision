package com.frc868.gui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;

import com.frc868.Camera;

/** 
 * @author Atif Niyaz
 * 
 * Creates the window for Vision on drivers
 */
@SuppressWarnings("serial")
public class WindowRealTime extends JFrame implements ActionListener {
	
	private Container pane;
	private Camera camera;
	
	private Timer timer;
	
	/**
	 * Creates a Window with a specified Camera
	 */
	public WindowRealTime(Camera camera, String title){
		
		this.camera = camera;
		
		this.timer = new Timer(0, (ActionListener) this);
		
		pane = this.getContentPane();
		JButton button = new JButton("Exit Vision RunTime");
		button.addActionListener(actionEvent -> {
			System.exit(22);
		});
		
		pane.add(button);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(title);
		
		this.pack();
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		timer.start();
	}

	/**
	 * This method is called as fast as possible by the timer and initiates repainting 
	 */
	public void actionPerformed(ActionEvent actionEvent) {
		camera.getProcessedFrame();
	}
}
