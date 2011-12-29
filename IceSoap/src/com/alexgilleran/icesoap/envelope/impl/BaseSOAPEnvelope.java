package com.alexgilleran.icesoap.envelope.impl;

import java.io.IOException;

import org.xmlpull.v1.XmlSerializer;

import com.alexgilleran.icesoap.envelope.SOAPEnvelope;
import com.alexgilleran.icesoap.xml.XMLElement;
import com.alexgilleran.icesoap.xml.XMLNode;
import com.alexgilleran.icesoap.xml.impl.XMLNodeImpl;

/**
 * Concrete implementation of SOAPEnv to be decorated. Automatically sets up the
 * basic namespaces, <envelope> tags etc, as well as creating a head and body
 * tag to be manipulated by decorators.
 * 
 * @author Alex Gilleran
 * 
 */
public class BaseSOAPEnvelope extends XMLNodeImpl implements SOAPEnvelope {
	/** The SOAP header element */
	private XMLNode header;
	/** The SOAP body element */
	private XMLNode body;

	/**
	 * Initialises the class - sets up the basic "soapenv", "soapenc", "xsd" and
	 * "xsi" namespaces present in all SOAP messages
	 */
	public BaseSOAPEnvelope() {
		super(NODE_NAMESPACE, NODE_NAME);

		this.declarePrefix(NS_PREFIX_SOAPENV, NS_URI_SOAPENV);
		this.declarePrefix(NS_PREFIX_SOAPENC, NS_URI_SOAPENC);
		this.declarePrefix(XMLElement.NS_PREFIX_XSD, XMLElement.NS_URI_XSD);
		this.declarePrefix(XMLElement.NS_PREFIX_XSI, XMLElement.NS_URI_XSI);

		header = this.addNode(NS_URI_SOAPENV, "Header");
		body = this.addNode(NS_URI_SOAPENV, "Body");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public XMLNode getHeader() {
		return header;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public XMLNode getBody() {
		return body;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(XmlSerializer cereal)
			throws IllegalArgumentException, IllegalStateException, IOException {
		cereal.startDocument(ENCODING_UTF8, true);

		super.serialize(cereal);

		cereal.endDocument();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((body == null) ? 0 : body.hashCode());
		result = prime * result + ((header == null) ? 0 : header.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseSOAPEnvelope other = (BaseSOAPEnvelope) obj;
		if (body == null) {
			if (other.body != null)
				return false;
		} else if (!body.equals(other.body))
			return false;
		if (header == null) {
			if (other.header != null)
				return false;
		} else if (!header.equals(other.header))
			return false;
		return true;
	}
}
