package com.alexgilleran.icesoap.request;

import com.alexgilleran.icesoap.soapfault.SOAP11Fault;

public interface ListRequest<ResultType> extends
		BaseListRequest<ResultType, SOAP11Fault> {

}
