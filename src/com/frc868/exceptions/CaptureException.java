package com.frc868.exceptions;

import java.io.IOException;

/**
 * @author Andrew Bass, Atif Niyaz
 * 
 * This exception is thrown when a VideoCapture object cannot open a video stream
 */
public class CaptureException extends IOException {
	private static final long serialVersionUID = -6718621310458369020L;

	public CaptureException() {
		super("Capture Exception was thrown");
	}
	
	public CaptureException(String message) {
		super(message);
	}
	
}
