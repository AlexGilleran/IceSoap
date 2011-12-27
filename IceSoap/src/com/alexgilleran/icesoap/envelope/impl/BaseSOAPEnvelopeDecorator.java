package com.alexgilleran.icesoap.envelope.impl;

import com.alexgilleran.icesoap.envelope.SOAPEnvelope;
import com.alexgilleran.icesoap.xml.XMLNode;

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
