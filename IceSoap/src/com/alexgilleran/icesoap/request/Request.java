package com.alexgilleran.icesoap.request;

import com.alexgilleran.icesoap.soapfault.SOAP11Fault;

public interface Request<ResultType> extends
		BaseRequest<ResultType, SOAP11Fault> {

}
