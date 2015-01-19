package com.frc868.gui;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.frc868.Server;

@SuppressWarnings("serial")
public class SliderView extends JPanel {
	
	public SliderView() {
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		add(new SpeedSlider(Server.getInstance()));
		add(new HSVSlider());
		add(new ServerOutput(Server.getInstance(), 100));
	}

}
