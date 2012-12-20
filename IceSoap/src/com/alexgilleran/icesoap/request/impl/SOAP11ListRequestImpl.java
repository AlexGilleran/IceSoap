/**
 * 
 */
package com.alexgilleran.icesoap.request.impl;

import java.util.List;

import com.alexgilleran.icesoap.envelope.SOAPEnvelope;
import com.alexgilleran.icesoap.request.ListRequest;
import com.alexgilleran.icesoap.request.SOAP11ListRequest;
import com.alexgilleran.icesoap.request.SOAPRequester;
import com.alexgilleran.icesoap.soapfault.SOAP11Fault;

/**
 * Extension of {@link ListRequestImpl} that defaults the SOAPFaultType to
 * {@link SOAP11Fault}
 * 
 * @author Alex Gilleran
 * 
 * @param <ResultType>
 *            The type of the object to retrieve from this request. If the type
 *            is a {@link List}, you may want to consider using
 *            {@link ListRequest} instead.
 */
public class SOAP11ListRequestImpl<ResultType> extends ListRequestImpl<ResultType, SOAP11Fault> implements
		SOAP11ListRequest<ResultType> {

	/**
	 * Creates a new list request.
	 * 
	 * @param url
	 *            The URL to post the request to
	 * @param soapEnv
	 *            The SOAP envelope to send, as a {@link SOAPEnvelope}
	 * @param soapAction
	 *            The SOAP Action to pass in the HTTP header - can be null
	 * @param resultClass
	 *            The class of the contents of the list.
	 * @param requester
	 *            The implementation of {@link SOAPRequester} to use for
	 *            requests.
	 */
	protected SOAP11ListRequestImpl(String url, SOAPEnvelope soapEnv, String soapAction, Class<ResultType> resultClass,
			SOAPRequester requester) {
		super(url, soapEnv, soapAction, resultClass, SOAP11Fault.class, requester);
	}

}
