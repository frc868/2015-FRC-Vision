package com.frc868.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
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
	
	private Server server;
	
	private JScrollPane pane;
	private JTable table;
	private DefaultTableModel model;
	
	private Timer timer;
	
	public ServerOutput(Server server, int delay) {
		super();
		
		this.server = server;
		this.timer = new Timer(delay, (ActionListener) this);
		
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(layout);
		
		this.model = new DefaultTableModel();
		model.addColumn("Key");
		model.addColumn("Value");
		
		this.table = new JTable(model);
		
		this.model.addRow(new Object[]{"yea", "ya"});
		
		this.pane = new JScrollPane(table);
		this.pane.setAlignmentY(TOP_ALIGNMENT);
		
		this.add(pane);
		this.setPreferredSize(new Dimension(200, 200));
		timer.start();
	}

	public void actionPerformed(ActionEvent actionEvent) {
		this.model = server.toTableModel();
		this.table.setModel(model);
		
		this.repaint();
	}
}