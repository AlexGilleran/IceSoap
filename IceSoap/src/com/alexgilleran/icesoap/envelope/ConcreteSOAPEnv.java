package com.alexgilleran.icesoap.envelope;

import java.io.IOException;
import java.io.StringWriter;

import org.xmlpull.v1.XmlSerializer;

import com.alexgilleran.icesoap.xml.XMLElement;
import com.alexgilleran.icesoap.xml.XMLNode;
import com.alexgilleran.icesoap.xml.impl.XMLNodeImpl;


import android.util.Xml;

/**
 * Concrete implementation of SOAPEnv to be decorated. Automatically sets up the
 * basic namespaces, <envelope> tags etc, as well as creating a head and body
 * tag to be manipulated by decorators.
 * 
 * @author Alex Gilleran
 * 
 */
public class ConcreteSOAPEnv extends XMLNodeImpl implements SOAPEnv {

	private XMLNode header;
	private XMLNode body;

	/**
	 * Initialises the class - sets up the basic "soapenv", "soapenc", "xsd" and
	 * "xsi" namespaces present in all SOAP messages
	 */
	public ConcreteSOAPEnv() {
		super(NODE_NAMESPACE, NODE_NAME);

		this.declarePrefix(NS_PREFIX_SOAPENV, NS_URI_SOAPENV);
		this.declarePrefix(NS_PREFIX_SOAPENC, NS_URI_SOAPENC);
		this.declarePrefix(XMLElement.NS_PREFIX_XSD, XMLElement.NS_URI_XSD);
		this.declarePrefix(XMLElement.NS_PREFIX_XSI, XMLElement.NS_URI_XSI);

		header = this.addNode(NS_URI_SOAPENV, "Header");
		body = this.addNode(NS_URI_SOAPENV, "Body");
	}

	/*
	 * (non-Javadoc)
	 * @see com.agilleran.android.soapdroid.envelope.SOAPEnv#getHeader()
	 */
	public XMLNode getHeader() {
		return header;
	}

	/*
	 * (non-Javadoc)
	 * @see com.agilleran.android.soapdroid.envelope.SOAPEnv#getBody()
	 */
	public XMLNode getBody() {
		return body;
	}

	/*
	 * (non-Javadoc)
	 * @see com.agilleran.android.soapdroid.xml.XMLElement#serialize(org.xmlpull.v1.XmlSerializer)
	 */
	@Override
	public void serialize(XmlSerializer cereal)
			throws IllegalArgumentException, IllegalStateException, IOException {
		cereal.startDocument(ENCODING_UTF8, true);

		super.serialize(cereal);

		cereal.endDocument();
	}
}
