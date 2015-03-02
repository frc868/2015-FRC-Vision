package com.frc868.gui;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ConstantField extends JPanel {
		
	private JLabel name;
	private JTextField values;
	
	public ConstantField(String name, String initVal) {
		BoxLayout vals = new BoxLayout(this, BoxLayout.X_AXIS);
		setLayout(vals);
		add(this.name = new JLabel(name));
		add(values = new JTextField(initVal));
		values.setMinimumSize(new Dimension(5, 100));
	}
}
