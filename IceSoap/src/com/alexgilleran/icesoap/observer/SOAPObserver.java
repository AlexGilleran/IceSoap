package com.alexgilleran.icesoap.observer;

import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.request.Request;
import com.alexgilleran.icesoap.soapfault.SOAP11Fault;

/**
 * Used to receive events from a running SOAP Request on the Android UI thread.
 * 
 * @author Alex Gilleran
 * 
 * @param <ReturnType>
 *            The type of the object that will be retrieved from this request.
 */
public interface SOAPObserver<ReturnType> extends
		BaseSOAPObserver<ReturnType, SOAP11Fault> {

	/**
	 * {@inheritDoc}
	 * 
	 * Note that this uses SOAP11Faults by default - if you need to consume SOAP
	 * 1.2 Faults, or need to parse extra details of the SOAP Fault, please use
	 * {@link BaseSOAPObserver} directly.
	 */
	public abstract void onException(Request<ReturnType, SOAP11Fault> request,
			SOAPException e);
}
