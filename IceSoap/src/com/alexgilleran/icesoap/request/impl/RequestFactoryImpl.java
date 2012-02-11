/**
 * 
 */
package com.alexgilleran.icesoap.request.impl;

import com.alexgilleran.icesoap.envelope.SOAPEnvelope;
import com.alexgilleran.icesoap.request.ListRequest;
import com.alexgilleran.icesoap.request.Request;
import com.alexgilleran.icesoap.request.RequestFactory;
import com.alexgilleran.icesoap.request.SOAPRequester;

/**
 * Factory for requests - retains a single instance of {@link SOAPRequester} to
 * save object creation. Doesn't retain state besides the {@link SOAPRequester},
 * so can be used as a Singleton if desired.
 * 
 * @author Alex Gilleran
 * 
 */
public class RequestFactoryImpl implements RequestFactory {
	private SOAPRequester requester;

	/**
	 * Instantiates a new {@link RequestFactoryImpl} with the default Apache
	 * HTTP SOAP Requester implementation
	 */
	public RequestFactoryImpl() {
		this(new ApacheSOAPRequester());
	}

	/**
	 * Instantiates a new RequestFactoryImpl, which will build requests and pass
	 * the supplied SOAPRequester
	 * 
	 * @param requester
	 *            The requester to use.
	 */
	public RequestFactoryImpl(SOAPRequester requester) {
		this.requester = requester;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <ReturnType> Request<ReturnType> buildRequest(String url,
			SOAPEnvelope soapEnvelope, String soapAction,
			Class<ReturnType> resultClass) {
		return new RequestImpl<ReturnType>(url, soapEnvelope, soapAction,
				resultClass, requester);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <ReturnType> ListRequest<ReturnType> buildListRequest(String url,
			SOAPEnvelope soapEnvelope, String soapAction,
			Class<ReturnType> resultClass) {
		return new ListRequestImpl<ReturnType>(url, soapEnvelope, soapAction,
				resultClass, requester);
	}
}
