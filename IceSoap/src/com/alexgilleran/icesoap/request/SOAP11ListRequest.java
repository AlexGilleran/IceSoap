package com.alexgilleran.icesoap.request;

import java.util.List;

import com.alexgilleran.icesoap.soapfault.SOAP11Fault;

/**
 * Extension of {@link ListRequest} that specifies a standard
 * {@link SOAP11Fault} rather than specifying a specific one. This is the same
 * interface as the v1.0.0 ListRequest.
 * 
 * @author Alex Gilleran
 * 
 * @param <ResultType>
 *            The type of the object to retrieve from this request. If the type
 *            is a {@link List}, you may want to consider using
 *            {@link ListRequest} instead.
 */
public interface SOAP11ListRequest<ResultType> extends
		ListRequest<ResultType, SOAP11Fault>, SOAP11Request<List<ResultType>> {

}
