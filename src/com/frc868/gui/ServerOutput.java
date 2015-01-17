package com.frc868.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

import com.frc868.Server;

/**
 * @author Andrew Bass
 * 
 * Displays the values being sent by the Server the the NetworkTable
 */
@SuppressWarnings("serial")
public class ServerOutput extends JComponent implements ActionListener {
	
	private Server server;
	
	private JTable table;
	private DefaultTableModel model;
	
	private Timer timer;
	
	public ServerOutput(Server server, int delay) {
		super();
		
		this.server = server;
		this.timer = new Timer(delay, (ActionListener) this);
		
		this.model = new DefaultTableModel();
		model.addColumn("Key");
		model.addColumn("Value");
		
		this.table = new JTable(model);
		
		this.model.addRow(new Object[]{"yea", "ya"});
		
		this.add(table);
		this.setPreferredSize(new Dimension(200, 200));
		timer.start();
	}

	public void actionPerformed(ActionEvent actionEvent) {
		this.repaint();
	}
}