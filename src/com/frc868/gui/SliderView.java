package com.frc868.gui;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

import com.frc868.Server;

@SuppressWarnings("serial")
public class SliderView extends JPanel {
	
	private JPanel serverOutput;
	private JPanel hsvSlider;
	private JPanel speedSlider;
	private JPanel resizeBox;
	
	public SliderView(CameraViewer camView) {
		
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(layout);
		
		serverOutput = new ServerOutput(Server.getInstance(), 100);
		serverOutput.setPreferredSize(new Dimension(80, 150));
		
		hsvSlider = new HSVSlider();
		speedSlider = new SpeedSlider(Server.getInstance());
		//resizeBox = new ResizeDropdown(camView);
		
		JComponent[] components = {serverOutput, hsvSlider, speedSlider, /*resizeBox */};
		
		for (JComponent comp : components) {
			add(comp);
		}
		
		setAlignmentY(TOP_ALIGNMENT);
		setAlignmentX(RIGHT_ALIGNMENT);
	}

}
