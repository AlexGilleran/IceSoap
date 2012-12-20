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
	/** The contents of the {@code faultCode} element, as a String. */
	@XMLField("faultcode")
	private String faultCode;
	/** The contents of the {@code faultString} element, as a String. */
	@XMLField("faultstring")
	private String faultString;
	/** The contents of the {@code faultActor} element, as a String. */
	@XMLField("faultactor")
	private String faultActor;

	/**
	 * Mandatory zero-arg constructor for initializing via reflection.
	 */
	public SOAP11Fault() {

	}

	/**
	 * Constructor used for testing.
	 * 
	 * @param faultCode
	 *            the fault code.
	 * @param faultString
	 *            The fault string.
	 * @param faultActor
	 *            The fault actor.
	 */
	public SOAP11Fault(String faultCode, String faultString, String faultActor) {
		this.faultCode = faultCode;
		this.faultString = faultString;
		this.faultActor = faultActor;
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((faultActor == null) ? 0 : faultActor.hashCode());
		result = prime * result + ((faultCode == null) ? 0 : faultCode.hashCode());
		result = prime * result + ((faultString == null) ? 0 : faultString.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SOAP11Fault other = (SOAP11Fault) obj;
		if (faultActor == null) {
			if (other.faultActor != null)
				return false;
		} else if (!faultActor.equals(other.faultActor))
			return false;
		if (faultCode == null) {
			if (other.faultCode != null)
				return false;
		} else if (!faultCode.equals(other.faultCode))
			return false;
		if (faultString == null) {
			if (other.faultString != null)
				return false;
		} else if (!faultString.equals(other.faultString))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SOAP11Fault [faultCode=" + faultCode + ", faultString=" + faultString + ", faultActor=" + faultActor
				+ "]";
	}
}
