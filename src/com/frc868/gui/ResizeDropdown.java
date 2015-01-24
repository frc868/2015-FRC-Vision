package com.frc868.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Andrew Bass and Atif Niyaz
 * 
 * Handles setting the scale variable in a CameraViewer
 */
@SuppressWarnings("serial")
public class ResizeDropdown extends JPanel implements ActionListener {
	
	private static Double[] SIZES = { 0.5, 0.75, 1.0, 1.25, 1.5, 1.75, 2.0};
	
	private CameraViewer viewer;
	
	private JLabel label;
	@SuppressWarnings("rawtypes")
	private JComboBox combo;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ResizeDropdown(CameraViewer viewer) {
		
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(layout);

		this.viewer = viewer;
		this.label = new JLabel("Image Scale");
		
		this.combo = new JComboBox(SIZES);
		this.combo.addActionListener(this);
		
		this.add(label);
		this.add(combo);
	}

	public void actionPerformed(ActionEvent arg0) {
		Double value = (Double) combo.getSelectedItem();
		viewer.setScale(value);
	}
	
}
