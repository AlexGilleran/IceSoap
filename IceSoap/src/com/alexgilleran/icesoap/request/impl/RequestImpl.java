/**
 * 
 */
package com.alexgilleran.icesoap.request.impl;

import com.alexgilleran.icesoap.envelope.SOAPEnvelope;
import com.alexgilleran.icesoap.request.Request;
import com.alexgilleran.icesoap.request.SOAPRequester;
import com.alexgilleran.icesoap.soapfault.SOAP11Fault;

/**
 * @author Alex Gilleran
 * 
 */
public class RequestImpl<ResultType> extends
		BaseRequestImpl<ResultType, SOAP11Fault> implements Request<ResultType> {

	/**
	 * @param url
	 * @param soapEnv
	 * @param soapAction
	 * @param resultClass
	 * @param faultTypeClass
	 * @param requester
	 */
	protected RequestImpl(String url, SOAPEnvelope soapEnv, String soapAction,
			Class<ResultType> resultClass, SOAPRequester requester) {
		super(url, soapEnv, soapAction, resultClass, SOAP11Fault.class,
				requester);
	}

}
