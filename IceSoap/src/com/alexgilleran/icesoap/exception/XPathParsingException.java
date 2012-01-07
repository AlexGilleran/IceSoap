package com.alexgilleran.icesoap.exception;

/**
 * Occurs when a problem is encountered while parsing a string-based XPath
 * expression.
 * 
 * @author Alex Gilleran
 * 
 */
public class XPathParsingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8402123122601551292L;

	public XPathParsingException() {
		super();
	}

	public XPathParsingException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public XPathParsingException(String arg0) {
		super(arg0);
	}

	public XPathParsingException(Throwable arg0) {
		super(arg0);
	}

}
