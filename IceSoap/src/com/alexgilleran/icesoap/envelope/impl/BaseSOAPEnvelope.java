package com.alexgilleran.icesoap.envelope.impl;

import java.io.IOException;

import org.xmlpull.v1.XmlSerializer;

import com.alexgilleran.icesoap.envelope.SOAPEnvelope;
import com.alexgilleran.icesoap.xml.XMLNode;
import com.alexgilleran.icesoap.xml.XMLParentNode;
import com.alexgilleran.icesoap.xml.impl.XMLParentNodeImpl;

public class BaseSOAPEnvelope extends XMLParentNodeImpl implements SOAPEnvelope {
	public final static String NS_PREFIX_SOAPENV = "soapenv";
	public final static String NS_PREFIX_SOAPENC = "soapenc";

	public final static String DEFAULT_ENCODING = "UTF-8";
	public final static String NODE_NAME = "Envelope";

	/** The SOAP header element */
	private XMLParentNode header;
	/** The SOAP body element */
	private XMLParentNode body;
	/** The encoding type */
	private String encoding = DEFAULT_ENCODING;

	public BaseSOAPEnvelope(String envelopeNs, String encodingNs) {
		super(envelopeNs, NODE_NAME);

		this.declarePrefix(NS_PREFIX_SOAPENV, envelopeNs);
		this.declarePrefix(NS_PREFIX_SOAPENC, encodingNs);
		this.declarePrefix(XMLNode.NS_PREFIX_XSD, XMLNode.NS_URI_XSD);
		this.declarePrefix(XMLNode.NS_PREFIX_XSI, XMLNode.NS_URI_XSI);

		header = this.addNode(envelopeNs, "Header");
		body = this.addNode(envelopeNs, "Body");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(XmlSerializer cereal)
			throws IllegalArgumentException, IllegalStateException, IOException {
		cereal.startDocument(DEFAULT_ENCODING, true);

		super.serialize(cereal);

		cereal.endDocument();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public XMLParentNode getHeader() {
		return header;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public XMLParentNode getBody() {
		return body;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getEncoding() {
		return encoding;
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
