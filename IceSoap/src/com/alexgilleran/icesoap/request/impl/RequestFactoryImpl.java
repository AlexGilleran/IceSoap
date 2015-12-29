/**
 * 
 */
package com.alexgilleran.icesoap.request.impl;

import com.alexgilleran.icesoap.envelope.SOAPEnvelope;
import com.alexgilleran.icesoap.request.*;

/**
 * Factory for requests - retains a single instance of {@link SOAPRequester} to
 * save object creation. Doesn't retain state besides the {@link SOAPRequester},
 * so can be used as a Singleton if desired.
 * 
 * @author Alex Gilleran
 * 
 */
public class RequestFactoryImpl implements RequestFactory {
	/** Requester to perform soap requests. */
	private SOAPRequester soapRequester;

	/**
	 * Instantiates a new {@link RequestFactoryImpl} with the default {@link HUCSOAPRequester}
	 * {@link SOAPRequester} implementation.
	 */
	public RequestFactoryImpl() {
		this(new HUCSOAPRequester());
	}

	/**
	 * Instantiates a new RequestFactoryImpl, which will build requests and pass
	 * the supplied SOAPRequester
	 * 
	 * @param requester
	 *            The requester to use.
	 */
	public RequestFactoryImpl(SOAPRequester requester) {
		this.soapRequester = requester;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <ReturnType, SOAPFaultType> Request<ReturnType, SOAPFaultType> buildRequest(String url,
																					   SOAPEnvelope soapEnvelope, String soapAction, Class<ReturnType> resultClass,
																					   Class<SOAPFaultType> soapFaultType) {
		return new RequestImpl<ReturnType, SOAPFaultType>(url, soapEnvelope, soapAction, resultClass, soapFaultType,
				soapRequester);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <ReturnType, SOAPFaultType> ListRequest<ReturnType, SOAPFaultType> buildListRequest(String url,
			SOAPEnvelope soapEnvelope, String soapAction, Class<ReturnType> resultClass,
			Class<SOAPFaultType> soapFaultType) {
		return new ListRequestImpl<ReturnType, SOAPFaultType>(url, soapEnvelope, soapAction, resultClass,
				soapFaultType, soapRequester);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <ReturnType> SOAP11Request<ReturnType> buildRequest(String url, SOAPEnvelope soapEnvelope,
															   String soapAction, Class<ReturnType> resultClass) {
		return new SOAP11RequestImpl<ReturnType>(url, soapEnvelope, soapAction, resultClass, soapRequester);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <ReturnType> SOAP11ListRequest<ReturnType> buildListRequest(String url, SOAPEnvelope soapEnvelope,
																	   String soapAction, Class<ReturnType> resultClass) {
		return new SOAP11ListRequestImpl<ReturnType>(url, soapEnvelope, soapAction, resultClass, soapRequester);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SOAPRequester getSOAPRequester() {
		return soapRequester;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSOAPRequester(SOAPRequester soapRequester) {
		this.soapRequester = soapRequester;
	}
}
