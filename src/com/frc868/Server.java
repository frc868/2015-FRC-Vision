package com.frc868;

import java.io.IOException;

import javax.swing.table.DefaultTableModel;

import edu.wpi.first.wpilibj.networktables.NetworkTable;


public class Server {
	
	private static Server instance = null;
	
	private double cam_width;
	private double center;
	private double distFactor;
	private double speed;
	
	private final double THRESHOLD = 20;
	private final double MAX_TURN_OFFSET = 1.0;
	
	private static Camera camera;
	private NetworkTable table;
	
	private Server(){
		NetworkTable.setTeam(868);
		NetworkTable.setIPAddress("10.8.68.2");
		NetworkTable.setClientMode();
		
		try {
			NetworkTable.initialize();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		table = NetworkTable.getTable("SmartDashboard");
		
		cam_width = camera.getResolution().getWidth();
		speed = 0;
	}
	
	public static Server getInstance(){
		if (instance == null){
			instance = new Server();
		}
		
		return instance;
	}
	
	public double getWidth() {
		return camera.getResolution().getWidth();
	}
	
	public NetworkTable getTable() {
		return this.table;
	}
	
	public static void setCamera(Camera camera) {
		Server.camera = camera;
	}
	
	public void setCenter(double center) {
		this.center = center;
	}
	
	public void setDistFactor(double factor){
		this.distFactor = factor;
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public double getCenter() {
		return center;
	}
	
	public double getOffset(){
		double offset =  (center - getCameraWidth() / 2.0) / getCameraWidth();
		offset *= 1.25;
	
		System.out.println(center);
		System.out.println(getCameraWidth());
		System.out.println(offset);
		return Math.min(offset, MAX_TURN_OFFSET);
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public double getCameraWidth() {
		return camera.getResolution().width;
	}
	
	public double getCameraHeight() {
		return camera.getResolution().height;
	}
	
	public boolean isRobotCenter() {
		return !isRobotRight() && !isRobotLeft();
	}
	
	public boolean isRobotRight() {
		return cam_width / 2 - THRESHOLD > center || center <= 0;
	}
	
	public boolean isRobotLeft() {
		return cam_width / 2 + THRESHOLD < center || center <= 0;
	}
	
	public double getDistanceFactor() {
		return distFactor;
	}
	
	public DefaultTableModel toTableModel() {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Key");
		model.addColumn("Value");
		
		model.addRow(new Object[] {"Offset", this.getOffset()} );
		model.addRow(new Object[] {"Dist Factor", this.getDistanceFactor()} );
		model.addRow(new Object[] {"Center", this.getCenter()} );
		
		return model;
	}
	
	public void send() {
		
		(new Thread(){
			
			public void run() {
				
				table.putNumber("Tote Offset Percentage", getOffset());
				table.putNumber("Tote Dist Factor", getDistanceFactor());
				table.putNumber("Tote Speed", speed);
				
				table.putBoolean("Tote Right Robot", isRobotRight());
				table.putBoolean("Tote Left Robot", isRobotLeft());
				table.putBoolean("Tote Center Robot", isRobotCenter());
			}
		}).start();
	}
	
	public boolean getIsTeleop() {
		return table.getBoolean("Teleop", false);
	}
}