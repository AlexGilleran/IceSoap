/**
 * 
 */
package com.alexgilleran.icesoap.observer;

import com.alexgilleran.icesoap.request.ListRequest;
import com.alexgilleran.icesoap.soapfault.SOAP11Fault;

/**
 * An extension of {@link SOAP11Observer} to deal with {@link ListRequest}s.
 * 
 * @author Alex Gilleran
 * 
 * @param <ReturnType>
 *            The type of the object that will be retrieved from this request.
 */
public interface SOAP11ListObserver<ReturnType> extends
		SOAPListObserver<ReturnType, SOAP11Fault> {
}
