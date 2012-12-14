/**
 * 
 */
package com.alexgilleran.icesoap.observer;

import com.alexgilleran.icesoap.soapfault.SOAP11Fault;

/**
 * An extension of {@link SOAP11Observer} to deal with {@link ListRequest}s,
 * pre-specifying the SOAP11Fault as the fault type to use.
 * 
 * @author Alex Gilleran
 * 
 * @param <ReturnType>
 *            The type of the object that will be retrieved from this request.
 */
public interface SOAP11ListObserver<ReturnType> extends SOAPListObserver<ReturnType, SOAP11Fault> {

}
