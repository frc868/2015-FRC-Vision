package com.frc868.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.frc868.Server;

/**
 * Displays the values being sent by the Server the the NetworkTable
 */
public class ServerOutput extends JComponent implements ActionListener {
	
	private static final String[] COLUMN_NAMES = {"Key", "Value" };
	
	private Server server;
	
	private JTable table;
	private DefaultTableModel model;
	
	private Timer timer;
	
	public ServerOutput(Server server, int delay) {
		this.server = server;
		this.timer = new Timer(delay, (ActionListener) this);
		
		this.model = new DefaultTableModel();
		model.addColumn("Key");
		model.addColumn("Value");
		
		this.table = new JTable(model);
		
		this.add(table);
		timer.start();
	}

	public void actionPerformed(ActionEvent actionEvent) {
		this.model.addRow(new Object[]{"yea", "ya"});
		System.out.println("yea");
		this.repaint();
	}
}