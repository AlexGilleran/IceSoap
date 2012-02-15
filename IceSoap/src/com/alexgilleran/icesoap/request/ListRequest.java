package com.alexgilleran.icesoap.request;

import com.alexgilleran.icesoap.soapfault.SOAP11Fault;

public interface ListRequest<RequestType> extends
		BaseListRequest<RequestType, SOAP11Fault> {

}
