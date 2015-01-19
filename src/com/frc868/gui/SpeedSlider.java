package com.frc868.gui;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.frc868.Server;

/**
 * @author Andrew Bass
 * 
 * This adjusts speed of robot
 */

@SuppressWarnings("serial")
public class SpeedSlider extends JPanel implements ChangeListener {
	
	private Server server;
	private JSlider slider;
	private JLabel label;
	
	public SpeedSlider(Server server){ 
		super();
		
		this.server = server;
	
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(layout);
		
		this.label = new JLabel("Speed: 0.65");
		
		this.slider = new JSlider(0, 255);
		this.slider.setAlignmentX(LEFT_ALIGNMENT);
		this.slider.setOrientation(JSlider.HORIZONTAL);
		
		this.slider.addChangeListener((ChangeListener) this);
		this.slider.setValue((int) (0.65 * 255.0));
		server.setSpeed(0.65);
		
		this.add(label);
		this.add(slider);
	}
	
	/**
	 * If speed changes, make sure that the server gets the new value.
	 */
	public void stateChanged(ChangeEvent e) {
		
		double value = (double) this.slider.getValue() / 255.0;
		
		label.setText("Speed: " + ((double) Math.round(value * 1000) / 1000)/* * 2.0*/);
		
		server.setSpeed((value)/* * 2.0*/);
	}	
}
