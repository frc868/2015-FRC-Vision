package com.frc868;

import java.io.IOException;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.opencv.core.Rect;

import edu.wpi.first.wpilibj.networktables.NetworkTable;


public class Server {
	
	private static Server instance = null;
	
	private double cam_width;
	private double center;
	private double distFactor;
	private double offset;
	private double speed;
	
	private double rectWidth;
	private double rectHeight;
	
	private final double THRESHOLD = 20;
	private final double MAX_TURN_OFFSET = 1.0;
	
	private static Camera camera;
	public NetworkTable table;
	
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
		setCameraCenter(cam_width/2);
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
	
	public void setCameraCenter(double offset) {
		this.offset = offset;
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
	
	public void setRect(Rect rect) {
		this.rectHeight = rect.height;
		this.rectWidth = rect.width;
	}
	
	public double getRectWidth() {
		return this.rectWidth;
	}
	
	public double getRectHeight() {
		return this.rectHeight;
	}
	
	public double getCenter() {
		return center;
	}
	
	public double getOffset(){
		double offset =  (center - this.offset) / getCameraWidth();
		offset *= 1.25 * this.distFactor;
	
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
		model.addRow(new Object[] {"Rect Width", this.getRectWidth()});
		model.addRow(new Object[] {"Rect Height", this.getRectHeight()});
		
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
				
				table.putNumber("Camera Width", camera.getResolution().width);
				table.putNumber("Camera Height", camera.getResolution().height);
				
				table.putBoolean("Running Vision", true);
			}
		}).start();
	}
	
	public boolean getIsTeleop() {
		return table.getBoolean("Teleop", false);
	}
}