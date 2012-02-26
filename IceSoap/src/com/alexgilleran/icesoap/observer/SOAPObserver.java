package com.alexgilleran.icesoap.observer;

import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.request.Request;

/**
 * Used to receive events from a running SOAP Request on the Android UI thread.
 * 
 * @author Alex Gilleran
 * 
 * @param <ReturnType>
 *            The type of the object that will be retrieved from this request.
 * @param <SOAPFaultType>
 *            The type of the class to use for SOAPFaults
 */
public interface SOAPObserver<ReturnType, SOAPFaultType> {

	/**
	 * Called when the running SOAP {@link Request} completes
	 * 
	 * @param request
	 *            The {@link Request} instance that has completed - retrieve the
	 *            result object from it using {@link Request#getResult()}
	 */
	public abstract void onCompletion(Request<ReturnType, SOAPFaultType> request);

	/**
	 * Called if the running SOAP request hits an exception during execution.
	 * 
	 * @param request
	 *            The {@link Request} instance that has encountered an
	 *            exception.
	 * @param e
	 *            The exception that's been encountered.
	 * @param soapFault
	 *            The relevant SOAPFault, if one has been encountered. If no
	 *            SOAP fault was encountered (e.g. if the service couldn't be
	 *            reached at all), this will be null.
	 */
	public abstract void onException(
			Request<ReturnType, SOAPFaultType> request, SOAPException e);

}