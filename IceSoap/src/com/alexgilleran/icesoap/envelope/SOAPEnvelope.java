package com.alexgilleran.icesoap.envelope;

import com.alexgilleran.icesoap.xml.XMLParentNode;

/**
 * A representation of a SOAP envelope. Methods to retrieve the SOAP Header and
 * Body tags are provided so they can be manipulated by decorators
 * 
 * SOAP Envelopes are intended to be used with the Decorator Design Pattern -
 * when building envelopes in your code, first construct an instance of
 * ConcreteSOAPEnv, then build your own classes that extend BaseSOAPEnvDecorator
 * and construct these, passing first ConcreteSOAPEnv then each Envelope as an
 * argument.
 * 
 * e.g. SOAPEnv concreteEnv = new ConcreteSOAPEnv(); SOAPEnv mySOAPEnv = new
 * MySOAPEnvDecorator(concreteEnv, myArguments); SOAPEnv myOtherSoapEnv = new
 * MyOtherSOAPEnvDecorator(mySOAPEnv, myOtherArguments);
 * 
 * @author Alex Gilleran
 * 
 */
public interface SOAPEnvelope extends XMLParentNode {

	/**
	 * Returns the <soapenv:Header> node of the envelope, to be modified
	 * 
	 * @return The header node of the envelope
	 */
	public XMLParentNode getHeader();

	/**
	 * Returns the <soapenv:Body> node of the envelope, to be modified
	 * 
	 * @return The body node of the envelope
	 */
	public XMLParentNode getBody();

	/**
	 * Sets the encoding of the envelope when it's serialised - defaults to
	 * "UTF-8"
	 * 
	 * @param encoding
	 *            As a string - the string should be the same format as would be
	 *            in an XML declaration - e.g. the "UTF-8" in
	 *            {@code <?xml version="1.0" encoding="UTF-8"?>}
	 */
	public void setEncoding(String encoding);

	/**
	 * 
	 * @return Gets the encoding as a String in the same format as would be in
	 *         an XML declaration - e.g. the "UTF-8" in
	 *         {@code <?xml version="1.0" encoding="UTF-8"?>}
	 */
	public String getEncoding();
}
