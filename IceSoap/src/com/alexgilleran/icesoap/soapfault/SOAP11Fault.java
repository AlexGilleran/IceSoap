package com.alexgilleran.icesoap.soapfault;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

/**
 * Represents consistent elements of a SOAP 1.1 Fault. Note that the contents of
 * the {@code <detail>} tag depends on the service being consumed - to parse
 * this, extend this class.
 * 
 * @author Alex Gilleran
 * 
 */
@XMLObject("//Fault")
public class SOAP11Fault {
	@XMLField("faultcode")
	private String faultCode;
	@XMLField("faultstring")
	private String faultString;
	@XMLField("faultactor")
	private String faultActor;

	/**
	 * @return The contents of the {@code faultCode} element, as a String.
	 */
	public String getFaultCode() {
		return faultCode;
	}

	/**
	 * 
	 * @return The contents of the {@code faultString} element, as a String.
	 */
	public String getFaultString() {
		return faultString;
	}

	/**
	 * 
	 * @return The contents of the {@code faultActor} element, as a String.
	 */
	public String getFaultActor() {
		return faultActor;
	}
}
