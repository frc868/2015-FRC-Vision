package com.frc868.gui;

import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.opencv.core.Scalar;

import com.frc868.filters.YellowTote;

/**
 * @author Andrew Bass, Atif Niyaz, Calvin Henry
 *
 * Creates a Window with three Slider Objects to modify and play with HSV
 * color values.
 */
@SuppressWarnings("serial")
public class HSVSlider extends JPanel implements ChangeListener{
	
//	final int H_RANGE = 12;
//	final int S_RANGE = 155;
//	final int V_RANGE = 115;
	
	public JSlider h = new JSlider(0, 255);
	public JSlider s = new JSlider(0, 255);
	public JSlider v = new JSlider(0, 255);
	
	public JSlider hl = new JSlider(0,255);
	public JSlider sl = new JSlider(0,255);
	public JSlider vl = new JSlider(0,255);
	
	public JLabel hLabel, sLabel, vLabel;
	
	Container pane;
	
	public HSVSlider(){
		
		super();
		
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(layout);
		
		Scalar[] range = YellowTote.getRange();
		
		Scalar low = range[0];
		Scalar high = range[1];
		
		System.out.println(low);
		System.out.println(high);
		
		hLabel = new JLabel("Hue Min: " + ((int) low.val[0]) 
				+ "  Hue Max: " + ((int) high.val[0]));
		sLabel = new JLabel("Sat Min: " + ((int) low.val[1]) 
				+ "  Sat Max: " + ((int) high.val[1]));
		vLabel = new JLabel("Val Min: " + ((int) low.val[2])
				+ "  Val Max: " + ((int) high.val[2]));

		h.setAlignmentX(LEFT_ALIGNMENT);
		h.setOrientation(JSlider.HORIZONTAL);
		h.addChangeListener(this);
		h.setValue((int) high.val[0]);
		
		s.setAlignmentX(LEFT_ALIGNMENT);
		s.setOrientation(JSlider.HORIZONTAL);
		s.addChangeListener(this);
		s.setValue((int) high.val[1]);

		
		v.setAlignmentX(LEFT_ALIGNMENT);
		v.setOrientation(JSlider.HORIZONTAL);
		v.addChangeListener(this);
		v.setValue((int) high.val[2]);
		
		hl.setAlignmentX(LEFT_ALIGNMENT);
		hl.setOrientation(JSlider.HORIZONTAL);
		hl.addChangeListener(this);
		hl.setValue((int) low.val[0]);
		
		sl.setAlignmentX(LEFT_ALIGNMENT);
		sl.setOrientation(JSlider.HORIZONTAL);
		sl.addChangeListener(this);
		sl.setValue((int) low.val[1]);

		
		vl.setAlignmentX(LEFT_ALIGNMENT);
		vl.setOrientation(JSlider.HORIZONTAL);
		vl.addChangeListener(this);
		vl.setValue((int) low.val[2]);

		
		add(hLabel);
		add(hl);
		add(h);
		
		
		add(sLabel);
		add(sl);
		add(s);
		
		add(vLabel);
		add(vl);
		add(v);
	}

	/**
	 * Print out to the Console the adjusted Low and High Threshold values for HSV
	 */
	public void stateChanged(ChangeEvent arg0) {
		
		Scalar low = new Scalar(hl.getValue(), sl.getValue(), vl.getValue());
		Scalar high = new Scalar(h.getValue(), s.getValue(), v.getValue());
		
		hLabel.setText("Hue Min: " + hl.getValue() + "  Hue Max: " + h.getValue());
		sLabel.setText("Sat Min: " + sl.getValue() + "  Sat Max: " + s.getValue());
		vLabel.setText("Val Min: " + vl.getValue() + "  Val Max: " + v.getValue());
		 
		YellowTote.defineRange(low, high);
	}
	
	public Scalar[] getHSVValues() {
		Scalar low = new Scalar(hl.getValue(), sl.getValue(), vl.getValue());
		Scalar high = new Scalar(h.getValue(), s.getValue(), v.getValue());
		
		return  new Scalar[] {low, high};
	}
}
 