package com.frc868;

import java.io.IOException;

import edu.wpi.first.wpilibj.networktables.NetworkTable;


public class Server {
	
	private static Server instance = null;
	
	private double cam_width;
	private double center;
	
	private final double THRESHOLD = 20;
	
	private static Camera camera;
	private NetworkTable table;
	
	private double offset;
	
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
	}
	
	public static Server getInstance(){
		if (instance == null){
			instance = new Server();
		}
		
		return instance;
	}
	
	public static void setCamera(Camera camera) {
		Server.camera = camera;
	}
	
	public void setCenter(double center) {
		this.center = center;
	}
	
	public double getCenter() {
		return center;
	}
	
	public void setCameraOffset(double offset) {
		this.offset = offset;
	}
	
	public double getOffset(){
		return (center - cam_width / 2.0) / cam_width;
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
		
		table.putBoolean("Tote Right Robot", this.isRobotRight());
		table.putBoolean("Tote Left Robot", this.isRobotLeft());
		table.putBoolean("Tote Center Robot", this.isRobotCenter());
	}
}