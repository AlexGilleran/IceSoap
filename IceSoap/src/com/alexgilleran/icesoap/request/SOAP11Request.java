package com.alexgilleran.icesoap.request;

import com.alexgilleran.icesoap.soapfault.SOAP11Fault;

public interface SOAP11Request<ResultType> extends
		Request<ResultType, SOAP11Fault> {

}
