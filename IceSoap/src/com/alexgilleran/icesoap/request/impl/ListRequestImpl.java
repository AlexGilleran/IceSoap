/**
 * 
 */
package com.alexgilleran.icesoap.request.impl;

import com.alexgilleran.icesoap.envelope.SOAPEnvelope;
import com.alexgilleran.icesoap.request.ListRequest;
import com.alexgilleran.icesoap.request.SOAPRequester;
import com.alexgilleran.icesoap.soapfault.SOAP11Fault;

/**
 * @author Alex Gilleran
 * 
 */
public class ListRequestImpl<ResultType> extends
		BaseListRequestImpl<ResultType, SOAP11Fault> implements
		ListRequest<ResultType> {

	/**
	 * @param url
	 * @param soapEnv
	 * @param soapAction
	 * @param resultClass
	 * @param soapFaultClass
	 * @param requester
	 */
	protected ListRequestImpl(String url, SOAPEnvelope soapEnv,
			String soapAction, Class<ResultType> resultClass,
			SOAPRequester requester) {
		super(url, soapEnv, soapAction, resultClass, SOAP11Fault.class,
				requester);
	}

}
