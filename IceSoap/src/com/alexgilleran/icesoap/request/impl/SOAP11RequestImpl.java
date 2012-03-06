/**
 * 
 */
package com.alexgilleran.icesoap.request.impl;

import com.alexgilleran.icesoap.envelope.SOAPEnvelope;
import com.alexgilleran.icesoap.request.SOAP11Request;
import com.alexgilleran.icesoap.request.SOAPRequester;
import com.alexgilleran.icesoap.soapfault.SOAP11Fault;

/**
 * Extension of {@link RequestImpl} that defaults the SOAPFaultType to
 * {@link SOAP11Fault}.
 * 
 * @author Alex Gilleran
 * 
 */
public class SOAP11RequestImpl<ResultType> extends
		RequestImpl<ResultType, SOAP11Fault> implements
		SOAP11Request<ResultType> {

	/**
	 * Creates a new request, automatically creating the parser.
	 * 
	 * @param url
	 *            The URL to post the request to
	 * @param soapEnv
	 *            The SOAP envelope to send, as a {@link SOAPEnvelope}
	 * @param soapAction
	 *            The SOAP Action to pass in the HTTP header - can be null
	 * @param resultClass
	 *            The class of the type to return from the request.
	 * @param requester
	 *            The implementation of {@link SOAPRequester} to use for
	 *            requests.
	 */
	protected SOAP11RequestImpl(String url, SOAPEnvelope soapEnv,
			String soapAction, Class<ResultType> resultClass,
			SOAPRequester requester) {
		super(url, soapEnv, soapAction, resultClass, SOAP11Fault.class,
				requester);
	}

}
