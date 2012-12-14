package com.alexgilleran.icesoap.envelope;

import com.alexgilleran.icesoap.xml.XMLParentNode;

/**
 * A representation of a SOAP envelope. Methods to retrieve the SOAP Header and
 * Body tags are provided so that content can be added programatically.
 * 
 * @author Alex Gilleran
 * 
 */
public interface SOAPEnvelope extends XMLParentNode {

	/**
	 * Returns the <soapenv:Header> node of the envelope, to be modified.
	 * 
	 * @return The header node of the envelope.
	 */
	XMLParentNode getHeader();

	/**
	 * Returns the <soapenv:Body> node of the envelope, to be modified.
	 * 
	 * @return The body node of the envelope.
	 */
	XMLParentNode getBody();

	/**
	 * Sets the encoding of the envelope when it's serialised - defaults to
	 * "UTF-8".
	 * 
	 * @param encoding
	 *            As a string - the string should be the same format as would be
	 *            in an XML declaration - e.g. the "UTF-8" in
	 *            {@code <?xml version="1.0" encoding="UTF-8"?>}.
	 */
	void setEncoding(String encoding);

	/**
	 * 
	 * @return Gets the encoding as a String in the same format as would be in
	 *         an XML declaration - e.g. the "UTF-8" in
	 *         {@code <?xml version="1.0" encoding="UTF-8"?>}.
	 */
	String getEncoding();
}
