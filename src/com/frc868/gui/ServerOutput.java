package com.frc868.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
public class ServerOutput extends JPanel implements ActionListener {
	
	private static final int COLUMN_WIDTH = 100;
	
	private Server server;
	
	private JScrollPane pane;
	private JTable table;
	private DefaultTableModel model;
	
	private Timer timer;
	
	public ServerOutput(Server server, int delay) {
		super();
		
		this.server = server;
		this.timer = new Timer(delay, (ActionListener) this);
		
		GridLayout layout = new GridLayout();
		this.setLayout(layout);
		
		this.model = new DefaultTableModel();
		this.table = new JTable(model);
		
		this.table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		this.pane = new JScrollPane(table);
	
		this.add(pane);
		timer.start();
	}

	public void actionPerformed(ActionEvent actionEvent) {
		this.model = server.toTableModel();
		this.table.setModel(model);
		
		this.repaint();
	}
}