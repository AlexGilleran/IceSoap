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
 */
public interface SOAPObserver<ReturnType> {

	/**
	 * Called when the running SOAP {@link Request} completes
	 * 
	 * @param request
	 *            The {@link Request} instance that has completed - retrieve the
	 *            result object from it using {@link Request#getResult()}
	 */
	public void onCompletion(Request<ReturnType> request);

	/**
	 * Called if the running SOAP request hits an exception during execution.
	 * 
	 * @param request
	 *            The {@link Request} instance that has encountered an
	 *            exception.
	 * @param e
	 *            The exception that's been encountered.
	 */
	public void onException(Request<ReturnType> request, SOAPException e);
}
