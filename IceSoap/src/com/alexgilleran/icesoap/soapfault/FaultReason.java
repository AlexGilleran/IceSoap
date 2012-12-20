package com.alexgilleran.icesoap.soapfault;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

/**
 * A soap fault reason, as returned from a SOAP 1.2 fault envelope. The envelope
 * will have one or more of these, each for different languages.
 * 
 * @author Alex Gilleran
 * 
 */
@XMLObject("//Reason/Text")
public class FaultReason {
	/** Lang field of the reason */
	@XMLField("@lang")
	private String lang;

	/** The reason itself. */
	@XMLField
	private String reason;

	/**
	 * @return The lang field of the reason
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * @return The reason itself.
	 */
	public String getReason() {
		return reason;
	}
}
