package com.frc868.gui;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.frc868.Server;

@SuppressWarnings("serial")
public class SliderView extends JPanel {
	
	private JPanel serverOutput;
	private JPanel hsvSlider;
	private JPanel speedSlider;
	
	public SliderView() {
		
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(layout);
		
		
		serverOutput = new ServerOutput(Server.getInstance(), 100);
		hsvSlider = new HSVSlider();
		speedSlider = new SpeedSlider(Server.getInstance());
		
		add(speedSlider);
		add(hsvSlider);
		add(serverOutput);
		
		serverOutput.setPreferredSize(new Dimension(80, 200));
	}

}
