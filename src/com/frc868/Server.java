package com.frc868;

import java.io.IOException;

import edu.wpi.first.wpilibj.networktables.NetworkTable;


public class Server {
	
	private static Server instance = null;
	
	private double cam_width;
	private double center;
	private double distFactor;
	private double offset;
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
		offset = 0;
		speed = 0;
	}
	
	public static Server getInstance(){
		if (instance == null){
			instance = new Server();
		}
		
		return instance;
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
		double offset =  (center - cam_width / 2.0) / cam_width;
		offset *= 1.25;
	
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
	
	public void sendToSmartDashboard() {
		
		table.putNumber("Tote Offset Percentage", this.getOffset());
		table.putNumber("Tote Dist Factor", this.distFactor);
		table.putNumber("Tote Speed", this.speed);
		
		table.putBoolean("Tote Right Robot", this.isRobotRight());
		table.putBoolean("Tote Left Robot", this.isRobotLeft());
		table.putBoolean("Tote Center Robot", this.isRobotCenter());
	}
}