package com.frc868.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.PrintWriter;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.opencv.core.Scalar;

import com.frc868.Server;

@SuppressWarnings("serial")
public class SliderView extends JPanel implements ActionListener {
	
	private JPanel serverOutput;
	private JPanel hsvSlider;
	private JPanel speedSlider;
	private JPanel resizeBox;
	
	private JButton saveButton;
	
	public SliderView(CameraViewer camView) {
		
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(layout);
		
		serverOutput = new ServerOutput(Server.getInstance(), 100);
		serverOutput.setPreferredSize(new Dimension(80, 150));
		
		hsvSlider = new HSVSlider();
		speedSlider = new SpeedSlider(Server.getInstance());
		saveButton = new JButton("Save HSV Values");
		saveButton.addActionListener(this);
		//resizeBox = new ResizeDropdown(camView);
		
		JComponent[] components = {serverOutput, hsvSlider, speedSlider, saveButton /*resizeBox */};
		
		for (JComponent comp : components) {
			add(comp);
		}
		
		setAlignmentY(TOP_ALIGNMENT);
		setAlignmentX(RIGHT_ALIGNMENT);
	}
	
	
	/**
	 * This method is called as fast as possible by the timer and initiates repainting 
	 */
	public void actionPerformed(ActionEvent actionEvent) {
		if(actionEvent.getSource() == saveButton){
			
			Scalar[] values = ((HSVSlider) hsvSlider).getHSVValues();
			
			try {
				BufferedWriter writer = new BufferedWriter(new PrintWriter("C:\\Vision2015\\HSV.txt"));
				
				writer.write(Double.toString(values[0].val[0]));
				writer.newLine();
				
				writer.write(Double.toString(values[0].val[1]));
				writer.newLine();
				
				writer.write(Double.toString(values[0].val[2]));
				writer.newLine();
				
				writer.write(Double.toString(values[1].val[0]));
				writer.newLine();
				
				writer.write(Double.toString(values[1].val[1]));
				writer.newLine();
				
				writer.write(Double.toString(values[1].val[2]));
				writer.newLine();
				
				writer.flush();
				writer.close();
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		this.revalidate();
		this.repaint();
	}

}
