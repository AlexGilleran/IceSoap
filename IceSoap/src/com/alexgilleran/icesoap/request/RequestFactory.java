/**
 * 
 */
package com.alexgilleran.icesoap.request;

import com.alexgilleran.icesoap.envelope.SOAPEnvelope;

/**
 * Creates Requests, passing them a default, Apache HTTP Client based
 * SOAPRequester implementation.
 * 
 * @author Alex Gilleran
 * 
 */
public interface RequestFactory {
	/**
	 * Builds a new {@link Request} for a non-list return type. Use this kind of
	 * request when you want only one returned object.
	 * 
	 * @param <ReturnType>
	 *            The type of the object that will be returned by this request
	 *            when executed (if successful)
	 * @param url
	 *            The url to POST the request to
	 * @param soapEnvelope
	 *            The {@link SOAPEnvelope} to post
	 * @param soapAction
	 *            The SOAP action to use. Can be set to null as some SOAP
	 *            services use this and some don't.
	 * @param resultClass
	 *            The class for of the return type.
	 * @return A request with the passed parameters, ready to have observers
	 *         registered and be executed.
	 */
	public <ReturnType> Request<ReturnType> buildRequest(String url,
			SOAPEnvelope soapEnvelope, String soapAction,
			Class<ReturnType> resultClass);

	/**
	 * Builds a {@link ListRequest} with the passed parameters. Use this sort of
	 * request when you want to get a list of the same object back from the
	 * service.
	 * 
	 * @param <ReturnType>
	 *            The type of the objects inside the list that will be returned
	 *            by this request when executed (if successful)
	 * @param url
	 *            The url to POST the request to
	 * @param soapEnvelope
	 *            The {@link SOAPEnvelope} to post
	 * @param soapAction
	 *            The SOAP action to use. Can be set to null as some SOAP
	 *            services use this and some don't.
	 * @param resultClass
	 *            The class to go inside the list (e.g. {@code List<ReturnType>}
	 *            ) for of the return type.
	 * @return A request with the passed parameters, ready to have observers
	 *         registered and be executed.
	 */
	public <ReturnType> ListRequest<ReturnType> buildListRequest(String url,
			SOAPEnvelope soapEnvelope, String soapAction,
			Class<ReturnType> resultClass);
}
