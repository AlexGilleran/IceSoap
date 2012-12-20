/**
 * 
 */
package com.alexgilleran.icesoap.request;

import com.alexgilleran.icesoap.envelope.SOAPEnvelope;
import com.alexgilleran.icesoap.request.impl.ApacheSOAPRequester;
import com.alexgilleran.icesoap.soapfault.SOAP11Fault;

/**
 * Creates Requests, passing them a default, Apache HTTP Client based
 * SOAPRequester implementation.
 * 
 * @author Alex Gilleran
 * 
 */
public interface RequestFactory {
	/**
	 * Builds a default {@link SOAP11Request} that will parse a default
	 * {@link SOAP11Fault} Fault in the event of HTTP Error 500.
	 * 
	 * @param <ReturnType>
	 *            The type of the object that will be returned by this request
	 *            when executed (if successful).
	 * @param url
	 *            The url to POST the request to.
	 * @param soapEnvelope
	 *            The {@link SOAPEnvelope} to post.
	 * @param soapAction
	 *            The SOAP action to use. Can be set to null as some SOAP
	 *            services use this and some don't.
	 * @param resultClass
	 *            The class for of the return type.
	 * @return A request with the passed parameters, ready to have observers
	 *         registered and be executed.
	 * @see #buildRequest(String, SOAPEnvelope, String, Class, Class)
	 */
	<ReturnType> SOAP11Request<ReturnType> buildRequest(String url, SOAPEnvelope soapEnvelope, String soapAction,
			Class<ReturnType> resultClass);

	/**
	 * Builds a new {@link Request} for a non-list return type. Use this kind of
	 * request when you want only one returned object, and want to specify a
	 * specific SOAPFault type.
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
	 * @param soapFaultClass
	 *            The class to use to parse the soap fault that will be returned
	 *            from the server in the event of an HTTP 500 error.
	 * @return A request with the passed parameters, ready to have observers
	 *         registered and be executed.
	 */
	<ReturnType, SOAPFaultType> Request<ReturnType, SOAPFaultType> buildRequest(String url, SOAPEnvelope soapEnvelope,
			String soapAction, Class<ReturnType> resultClass, Class<SOAPFaultType> soapFaultClass);

	/**
	 * Creates a {@link ListRequest} that will parse a default
	 * {@link SOAP11Fault} if an HTTP 500 error is encountered. Use this sort of
	 * request when you want to get a list of the same object back from the
	 * service, and you only want basic details back if there's an error.
	 * 
	 * @param <ReturnType>
	 *            The type of the objects inside the list that will be returned
	 *            by this request when executed (if successful).
	 * @param url
	 *            The url to POST the request to.
	 * @param soapEnvelope
	 *            The {@link SOAPEnvelope} to post.
	 * @param soapAction
	 *            The SOAP action to use. Can be set to null as some SOAP
	 *            services use this and some don't.
	 * @param resultClass
	 *            The class to go inside the list (e.g. {@code List<ReturnType>}
	 *            ) for of the return type.
	 * @return A request with the passed parameters, ready to have observers
	 *         registered and be executed.
	 */
	<ReturnType> SOAP11ListRequest<ReturnType> buildListRequest(String url, SOAPEnvelope soapEnvelope,
			String soapAction, Class<ReturnType> resultClass);

	/**
	 * Builds a {@link ListRequest} with the passed parameters. Use this sort of
	 * request when you want to get a list of the same object back from the
	 * service, and you want to specify a certain class to parse in the event of
	 * an exception.
	 * 
	 * @param <ReturnType>
	 *            The type of the objects inside the list that will be returned
	 *            by this request when executed (if successful).
	 * @param url
	 *            The url to POST the request to.
	 * @param soapEnvelope
	 *            The {@link SOAPEnvelope} to post.
	 * @param soapAction
	 *            The SOAP action to use. Can be set to null as some SOAP
	 *            services use this and some don't.
	 * @param resultClass
	 *            The class to go inside the list (e.g. {@code List<ReturnType>}
	 *            ) for of the return type.
	 * @param soapFaultClass
	 *            The class to use to parse the soap fault that will be returned
	 *            from the server in the event of an HTTP 500 error.
	 * @return A request with the passed parameters, ready to have observers
	 *         registered and be executed.
	 */
	<ReturnType, SOAPFaultType> ListRequest<ReturnType, SOAPFaultType> buildListRequest(String url,
			SOAPEnvelope soapEnvelope, String soapAction, Class<ReturnType> resultClass,
			Class<SOAPFaultType> soapFaultClass);

	/**
	 * Sets the implementation of {@link SOAPRequester} that will be used when
	 * creating requests. If this isn't used, the default
	 * {@link ApacheSOAPRequester} implementation will be used.
	 * 
	 * @param soapRequester
	 */
	void setSOAPRequester(SOAPRequester soapRequester);

	/**
	 * Gets the instance of {@link SOAPRequester} currently being used for
	 * creating requests.
	 * 
	 * @return The instance of {@link SOAPRequester}.
	 */
	SOAPRequester getSOAPRequester();
}
