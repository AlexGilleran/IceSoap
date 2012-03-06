package com.alexgilleran.icesoap.request;

import java.util.List;

import com.alexgilleran.icesoap.observer.SOAP11Observer;
import com.alexgilleran.icesoap.soapfault.SOAP11Fault;

/**
 * Encapsulates all the code for making a SOAP Request - to use, create an
 * object that implements the {@link SOAP11Observer} class (generally this is
 * easiest using anonymous objects from the activity or fragment) and attach it
 * using the {@link Request#registerObserver(SOAP11Observer)} method. Then
 * invoke {@link Request#execute()} - the request will be performed on a
 * background thread, and methods of the {@link SOAP11Observer} will be called
 * on the UI thread.
 * 
 * This extension of the standard {@link Request} interface already specifies
 * the default {@link SOAP11Fault} - it is the same as the v1.0.0
 * {@link Request} interface.
 * 
 * @author Alex Gilleran
 * 
 * @param <ResultType>
 *            The type of the object to retrieve from this request. If the type
 *            is a {@link List}, you may want to consider using
 *            {@link ListRequest} instead.
 */
public interface SOAP11Request<ResultType> extends
		Request<ResultType, SOAP11Fault> {

}
