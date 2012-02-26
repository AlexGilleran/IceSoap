package com.alexgilleran.icesoap.request;

import java.util.List;

import com.alexgilleran.icesoap.soapfault.SOAP11Fault;

public interface SOAP11ListRequest<ResultType> extends
		ListRequest<ResultType, SOAP11Fault>, SOAP11Request<List<ResultType>> {

}
