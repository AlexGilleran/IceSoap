package com.alexgilleran.icesoap.exception;

import com.alexgilleran.icesoap.parser.IceSoapParser;

/**
 * Exception that occurs when a class definition is not valid for use with
 * {@link IceSoapParser}s.
 * 
 * @author Alex Gilleran
 * 
 */
public class ClassDefException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4086594115851592824L;

	public ClassDefException() {
	}

	public ClassDefException(String message) {
		super(message);
	}

	public ClassDefException(Throwable cause) {
		super(cause);
	}

	public ClassDefException(String message, Throwable cause) {
		super(message, cause);
	}

}
