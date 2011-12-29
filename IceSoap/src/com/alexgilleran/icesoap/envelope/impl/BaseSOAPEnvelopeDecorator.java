package com.alexgilleran.icesoap.envelope.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.xmlpull.v1.XmlSerializer;

import com.alexgilleran.icesoap.envelope.SOAPEnvelope;
import com.alexgilleran.icesoap.xml.XMLAttribute;
import com.alexgilleran.icesoap.xml.XMLElement;
import com.alexgilleran.icesoap.xml.XMLNode;
import com.alexgilleran.icesoap.xml.XMLTextElement;

/**
 * Base class for SOAP Envelope decorators
 * 
 * @author Alex Gilleran
 * 
 */
public abstract class BaseSOAPEnvelopeDecorator implements SOAPEnvelope {

	/** The envelope that this decorator is wrapping */
	private SOAPEnvelope wrappedEnvelope;

	/**
	 * Creates a SOAP envelope decorator with a default wrapped envelope.
	 */
	public BaseSOAPEnvelopeDecorator() {
		this(new ConcreteSOAPEnvelope());
	}

	/**
	 * Creates a new SOAP Envelope Decorator
	 * 
	 * @param wrappedEnvelope
	 *            The envelope to wrap
	 */
	public BaseSOAPEnvelopeDecorator(SOAPEnvelope wrappedEnvelope) {
		if (wrappedEnvelope == null) {
			wrappedEnvelope = new ConcreteSOAPEnvelope();
		}

		this.wrappedEnvelope = wrappedEnvelope;
	}

	/**
	 * Gets the envelope that is being wrapped
	 * 
	 * @return The envelope being wrapped.
	 */
	protected SOAPEnvelope getWrappedEnvelope() {
		return wrappedEnvelope;
	}

	public List<XMLElement> getSubElements() {
		return wrappedEnvelope.getSubElements();
	}

	public XMLNode addNode(String namespace, String name) {
		return wrappedEnvelope.addNode(namespace, name);
	}

	public Collection<XMLAttribute> getAttributes() {
		return wrappedEnvelope.getAttributes();
	}

	public XMLElement addElement(XMLElement element) {
		return wrappedEnvelope.addElement(element);
	}

	public XMLAttribute addAttribute(String namespace, String name, String value) {
		return wrappedEnvelope.addAttribute(namespace, name, value);
	}

	public XMLTextElement addTextElement(String namespace, String name,
			String value) {
		return wrappedEnvelope.addTextElement(namespace, name, value);
	}

	public void setType(String type) {
		wrappedEnvelope.setType(type);
	}

	public void declarePrefix(String prefix, String namespace) {
		wrappedEnvelope.declarePrefix(prefix, namespace);
	}

	public String getName() {
		return wrappedEnvelope.getName();
	}

	public void setName(String name) {
		wrappedEnvelope.setName(name);
	}

	public String getNamespace() {
		return wrappedEnvelope.getNamespace();
	}

	public void setNamespace(String namespace) {
		wrappedEnvelope.setNamespace(namespace);
	}

	public void serialize(XmlSerializer serializer)
			throws IllegalArgumentException, IllegalStateException, IOException {
		wrappedEnvelope.serialize(serializer);
	}

	public String toString() {
		return wrappedEnvelope.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public XMLNode getHeader() {
		return wrappedEnvelope.getHeader();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public XMLNode getBody() {
		return wrappedEnvelope.getBody();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((wrappedEnvelope == null) ? 0 : wrappedEnvelope.hashCode());
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
		BaseSOAPEnvelopeDecorator other = (BaseSOAPEnvelopeDecorator) obj;
		if (wrappedEnvelope == null) {
			if (other.wrappedEnvelope != null)
				return false;
		} else if (!wrappedEnvelope.equals(other.wrappedEnvelope))
			return false;
		return true;
	}

}
