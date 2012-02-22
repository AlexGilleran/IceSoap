package com.alexgilleran.icesoap.request;

import java.util.List;

import com.alexgilleran.icesoap.soapfault.SOAP11Fault;

public interface ListRequest<ResultType> extends
		BaseListRequest<ResultType, SOAP11Fault>, Request<List<ResultType>> {

}
