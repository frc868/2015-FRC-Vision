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

import com.frc868.Constants;
import com.frc868.FileIO;
import com.frc868.Server;

@SuppressWarnings("serial")
public class SliderView extends JPanel implements ActionListener {
	
	private JPanel serverOutput;
	private JPanel hsvSlider;
	
	private JButton saveButton;
	private JButton updateButton;
	
	public SliderView(CameraViewer camView) {
		
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(layout);
		
		serverOutput = new ServerOutput(Server.getInstance(), 100);
		serverOutput.setPreferredSize(new Dimension(80, 150));
		
		hsvSlider = new HSVSlider();
		saveButton = new JButton("Save HSV Values");
		updateButton = new JButton("Update Constants");
		saveButton.addActionListener(this);
		updateButton.addActionListener(actionListener -> {
			try {
				FileIO.updateConstants(Constants.CONST_PATH);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		JComponent[] components = {serverOutput, hsvSlider, saveButton, updateButton};
		
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
				BufferedWriter writer = new BufferedWriter(new PrintWriter(Constants.FILE_SAVE_PATH));
				
				for(Scalar val : values)
					for(int i = 0; i < 3; i++) {
						writer.write(Double.toString(val.val[i]));
						writer.newLine();
					}
				
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
