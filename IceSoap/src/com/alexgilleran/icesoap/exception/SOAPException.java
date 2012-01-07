package com.alexgilleran.icesoap.exception;

/**
 * Occurs due to problems with SOAP communication - this takes into account
 * connectivity issues, and may also wrap an {@link XMLParsingException} that
 * occurs when parsing a SOAP response.
 * 
 * @author Alex Gilleran
 * 
 */
public class SOAPException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -192040926748488461L;

	public SOAPException() {
		super();
	}

	public SOAPException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public SOAPException(String arg0) {
		super(arg0);
	}

	public SOAPException(Throwable arg0) {
		super(arg0);
	}
}
