package com.alexgilleran.icesoap.envelope;

import java.io.IOException;

import com.alexgilleran.icesoap.xml.XMLNode;

/**
 * Base class for SOAP Envelope decorators
 * 
 * @author Alex Gilleran
 * 
 */
public abstract class BaseSOAPEnvDecorator implements SOAPEnv {
	SOAPEnv wrappedEnvelope;

	public BaseSOAPEnvDecorator(SOAPEnv wrappedEnvelope) {
		if (wrappedEnvelope == null) {
			wrappedEnvelope = new ConcreteSOAPEnv();
		}

		this.wrappedEnvelope = wrappedEnvelope;
	}

	protected SOAPEnv getWrappedEnvelope() {
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
