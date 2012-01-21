/**
 * 
 */
package com.alexgilleran.icesoap.exception;

/**
 * Occurs when a problem is encountered in parsing an XML document.
 * 
 * @author Alex Gilleran
 * 
 */
public class XMLParsingException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3104808313795070711L;

	public XMLParsingException() {
		super();
	}

	public XMLParsingException(String message, Throwable cause) {
		super(message, cause);
	}

	public XMLParsingException(String message) {
		super(message);
	}

	public XMLParsingException(Throwable cause) {
		super(cause);
	}
}
