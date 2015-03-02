package com.frc868.gui;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class ConstantsEditor extends JPanel {
	
	private ConstantField camFeedUrl, maxDrivePow, powReduction, offsetFact, camPercent;
	
	public ConstantsEditor() {
		System.out.println("we are here");
		
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		
		camFeedUrl = new ConstantField("Camera Feed URL", "");
		maxDrivePow = new ConstantField("Max Drive Pow", "");
		powReduction = new ConstantField("Pow Decrease Rate", "");
		offsetFact = new ConstantField("Offset Factor", "");
		camPercent = new ConstantField("Percent Camera", "");
		
		setLayout(layout);
		add(camFeedUrl);
		add(maxDrivePow);
		add(powReduction);
		add(offsetFact);
		add(camPercent);
	}
}
