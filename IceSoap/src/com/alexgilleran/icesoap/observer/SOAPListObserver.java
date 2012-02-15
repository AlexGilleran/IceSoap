/**
 * 
 */
package com.alexgilleran.icesoap.observer;

import com.alexgilleran.icesoap.request.BaseListRequest;
import com.alexgilleran.icesoap.soapfault.SOAP11Fault;

/**
 * An extension of {@link SOAPObserver} to deal with {@link BaseListRequest}s.
 * 
 * @author Alex Gilleran
 * 
 * @param <ReturnType>
 *            The type of the object that will be retrieved from this request.
 */
public interface SOAPListObserver<ReturnType> extends
		BaseSOAPListObserver<ReturnType, SOAP11Fault> {
}
