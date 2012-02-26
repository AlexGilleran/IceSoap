package com.alexgilleran.icesoap.envelope.impl;

public class BaseSOAP12Envelope extends BaseSOAPEnvelope {
	public final static String NS_URI_SOAPENV = "http://www.w3.org/2003/05/soap-envelope";
	public final static String NS_URI_SOAPENC = "http://www.w3.org/2003/05/soap-encoding";

	/**
	 * Initialises the class - sets up the basic "soapenv", "soapenc", "xsd" and
	 * "xsi" namespaces present in all SOAP messages
	 */
	public BaseSOAP12Envelope() {
		super(NS_URI_SOAPENV, NS_URI_SOAPENC);
	}
}
