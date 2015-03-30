package com.frc868.arduino;

import jssc.SerialPort;
import jssc.SerialPortException;

/**
 * @author paul__000
 *
 */
public class Arduino {
	
	private static Arduino instance;
	
	private SerialPort _Serial;
	private int _BaudRate = SerialPort.BAUDRATE_9600;
	private int _DataBits = SerialPort.DATABITS_8;
	private int _StopBits = SerialPort.STOPBITS_1;
	private int _Parity = SerialPort.PARITY_NONE;
	private boolean _Rts = false;
	private boolean _Dtr = false;
	
	private static double MAX_LIFT_HEIGHT = 4;
	private static double ARDUINO_SCALAR = 64;
	
	private boolean isOpen = false;

	byte[] buffer = new byte[2048];
	int bufferPos = 0;
	
	public static Arduino getInstance() {
		if(instance == null)
			instance = new Arduino("COM13");
		return instance;
	}
	
	public void write(byte[] bytes) {
		try {
			if(!isOpen) {
				open(SerialPort.BAUDRATE_9600);
				isOpen = true;
			}
			send(bytes);
		} catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public void setHeight(double height) {
		if (height < 0){
			System.err.println("********* START OF AUTON **********");
			write(new byte[]{68});
			return;
		}
		int temp = (int) Math.max(Math.min((height / MAX_LIFT_HEIGHT) * ARDUINO_SCALAR, ARDUINO_SCALAR - 1), 0);
		System.out.println("Height: " + temp + " (from " + height + ")");
		write(new byte[] {(byte) temp});
	}
	
//	public static void main(String[] args) {
//		String comPort = (args.length >= 2) ? args[1] : "COM4";
//		int baudRate = (args.length >= 3) ? Integer.parseInt(args[2]) : SerialPort.BAUDRATE_9600;
//		
//		Arduino ardi = new Arduino(comPort);
//		try {
//			ardi.open(baudRate);
//			int lineCnt = 0;
//			long lastReq = 0;
//			
//			while (lineCnt < 30) {
//				
//				// Once a second send out "root\r\n"
//				long now = System.currentTimeMillis();
//				if (now - lastReq > 10) {
//					lastReq = now;
//					byte[] newLine = { '1', '2', '3', '4', '5', '6', '7', '8' ,'9', '0', '\n'};
//					ardi.send(newLine);
//					lineCnt++;
//				}
//				
//				// See if we have received any data
//				byte[] data = ardi.read();
//				if (data != null) {
//					// Do a hex dump of the data received
//					System.out.print("Received " + data.length + " bytes:");
//					for (int i = 0; i < data.length; i++) {
//						System.out.print((char) data[i]);
//					}
//					System.out.println();
//				} else {
//					Thread.sleep(10);
//				}
//			}
//			
//			ardi.close();
//
//		} catch (SerialPortException sexp) {
//			sexp.printStackTrace();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * @return Array of all bytes read in from serial port, or null if no bytes available.
	 * @throws SerialPortException 
	 */
	public byte[] read() throws SerialPortException {
		return _Serial.readBytes();
	}

	/**
	 * @param newLine
	 * @throws SerialPortException 
	 */
	private boolean send(byte[] buffer) throws SerialPortException {
		return _Serial.writeBytes(buffer);
	}

	public Arduino(String portName) {
		_Serial = new SerialPort(portName);
	}
		
	public void open() throws SerialPortException {
		open(_BaudRate);
	}
	
	public void open(int baudRate) throws SerialPortException {
		open(baudRate, _DataBits, _StopBits, _Parity);
	}
	
	public void open(int baudRate, int dataBits, int stopBits, int parityBits) throws SerialPortException {
		open(baudRate, dataBits, stopBits, parityBits, _Rts, _Dtr);
	}

	public void open(int baudRate, int dataBits, int stopBits, int parityBits, boolean rts, boolean dtr) throws SerialPortException {
		_BaudRate = baudRate;
		_DataBits = dataBits;
		_StopBits = stopBits;
		_Parity = parityBits;
		_Rts = rts;
		_Dtr = dtr;
		if (_Serial.isOpened() == true) {
			_Serial.closePort();
		}
		_Serial.openPort();
		_Serial.setParams(_BaudRate, _DataBits, _StopBits, _Parity, _Rts, _Dtr);
	}
	
	public void close() throws SerialPortException {
		if (_Serial.isOpened()) {
			_Serial.closePort();
		}
	}
}

