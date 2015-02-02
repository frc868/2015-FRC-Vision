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
import com.frc868.Server;

/** 
 * @author Atif Niyaz, Calvin Henry, Andrew Bass
 * 
 * Creates a new Window with the Camera
 */
@SuppressWarnings("serial")
public class RunWindow extends JFrame implements ActionListener {
	
	private JButton save;
	private Container pane;
	
	/**
	 * Creates a Window with a specified Camera
	 */
	public RunWindow(String title){
		
		pane = this.getContentPane();
		JButton button = new JButton("Exit Vision RunTime");
		button.addActionListener(this);
		
		pane.add(button);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(title);
		
		this.pack();
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	/**
	 * This method is called as fast as possible by the timer and initiates repainting 
	 */
	public void actionPerformed(ActionEvent actionEvent) {
		System.exit(22);
	}
}
