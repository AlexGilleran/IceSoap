package com.alexgilleran.icesoap.envelope;

import com.alexgilleran.icesoap.xml.XMLNode;

/**
 * Base class for SOAP Envelope decorators
 * 
 * @author Alex Gilleran
 * 
 */
public abstract class BaseSOAPEnvDecorator implements SOAPEnvelope {
	SOAPEnvelope wrappedEnvelope;

	public BaseSOAPEnvDecorator(SOAPEnvelope wrappedEnvelope) {
		if (wrappedEnvelope == null) {
			wrappedEnvelope = new ConcreteSOAPEnv();
		}

		this.wrappedEnvelope = wrappedEnvelope;
	}

	protected SOAPEnvelope getWrappedEnvelope() {
		return wrappedEnvelope;
	}

	@Override
	public XMLNode getHeader() {
		return wrappedEnvelope.getHeader();
	}

	@Override
	public XMLNode getBody() {
		return wrappedEnvelope.getBody();
	}
}
