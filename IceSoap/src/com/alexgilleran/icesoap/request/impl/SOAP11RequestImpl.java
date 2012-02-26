/**
 * 
 */
package com.alexgilleran.icesoap.request.impl;

import com.alexgilleran.icesoap.envelope.SOAPEnvelope;
import com.alexgilleran.icesoap.request.SOAP11Request;
import com.alexgilleran.icesoap.request.SOAPRequester;
import com.alexgilleran.icesoap.soapfault.SOAP11Fault;

/**
 * @author Alex Gilleran
 * 
 */
public class SOAP11RequestImpl<ResultType> extends
		RequestImpl<ResultType, SOAP11Fault> implements SOAP11Request<ResultType> {

	/**
	 * @param url
	 * @param soapEnv
	 * @param soapAction
	 * @param resultClass
	 * @param faultTypeClass
	 * @param requester
	 */
	protected SOAP11RequestImpl(String url, SOAPEnvelope soapEnv, String soapAction,
			Class<ResultType> resultClass, SOAPRequester requester) {
		super(url, soapEnv, soapAction, resultClass, SOAP11Fault.class,
				requester);
	}

}
