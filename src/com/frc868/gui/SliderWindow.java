package com.frc868.gui;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.opencv.core.Scalar;

import com.frc868.filters.YellowTote;

	//kill Dobie;

public class SliderWindow extends JFrame implements ChangeListener{
	final int H_RANGE = 12;
	final int S_RANGE = 155;
	final int V_RANGE = 115;
	
	public JSlider h = new JSlider(0,180 - H_RANGE);
	public JSlider s = new JSlider(0, 255 - S_RANGE);
	public JSlider v = new JSlider(0, 255 - V_RANGE);
	
	Container pane;
	
	public SliderWindow(){
		pane = this.getContentPane();
		FlowLayout flow = new FlowLayout();
		flow.setAlignment(FlowLayout.TRAILING);
		pane.setLayout(flow);
		
		this.setLocationRelativeTo(null);
		
		pane.add(h);
		pane.add(s);
		pane.add(v);
		
		h.addChangeListener(this);
		s.addChangeListener(this);
		v.addChangeListener(this);
		
		this.pack();
		this.setVisible(true);
	}


	public void stateChanged(ChangeEvent arg0) {
		Scalar low = new Scalar(h.getValue(), s.getValue(), v.getValue());
		Scalar high = new Scalar(h.getValue() + H_RANGE, s.getValue() + S_RANGE, v.getValue() + V_RANGE);
		
		System.out.println(low);
		System.out.println(high);
		
		YellowTote.defineRange(low, high);
	}
	
	
}
 