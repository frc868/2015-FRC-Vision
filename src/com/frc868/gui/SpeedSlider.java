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
		
		this.label = new JLabel("Speed");
		
		this.slider = new JSlider(0, 255);
		this.slider.setAlignmentX(CENTER_ALIGNMENT);
		this.slider.setOrientation(JSlider.VERTICAL);
		this.slider.addChangeListener((ChangeListener) this);
		
		this.add(label);
		this.add(slider);
	}
	
	/**
	 * If speed changes, make sure that the server gets the new value.
	 */
	public void stateChanged(ChangeEvent e) {
		server.setSpeed(((double)this.slider.getValue() / 255.0) * 2.0);
	}	
}
