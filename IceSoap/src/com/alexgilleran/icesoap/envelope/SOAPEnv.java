package com.alexgilleran.icesoap.envelope;

import java.io.IOException;

import com.alexgilleran.icesoap.xml.XMLNode;

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
public interface SOAPEnv {
	public final static String NS_PREFIX_SOAPENV = "soapenv";
	public final static String NS_URI_SOAPENV = "http://schemas.xmlsoap.org/soap/envelope/";
	public final static String NS_PREFIX_SOAPENC = "soapenc";
	public final static String NS_URI_SOAPENC = "http://schemas.xmlsoap.org/soap/encoding/";
	public final static String NODE_NAME = "Envelope";
	public final static String NODE_NAMESPACE = NS_URI_SOAPENV;

	public final static String ENCODING_UTF8 = "UTF-8";

	/**
	 * Returns the <soapenv:Header> node of the envelope, to be modified
	 * 
	 * @return The header node of the envelope
	 */
	public XMLNode getHeader();

	/**
	 * Returns the soapenv:Body node of the envelope, to be modified
	 * 
	 * @return The body node of the envelope
	 */
	public XMLNode getBody();
}
