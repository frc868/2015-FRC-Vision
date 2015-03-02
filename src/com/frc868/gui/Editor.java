package com.frc868.gui;

import java.awt.FlowLayout;

import javax.swing.JFrame;

public class Editor extends JFrame {
	
	public Editor() {
		
		getContentPane().add(new ConstantsEditor());
		getContentPane().setLayout(new FlowLayout(FlowLayout.LEADING));
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Constants Editor");
		
		this.pack();
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
	}

}
