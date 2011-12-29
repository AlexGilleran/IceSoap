package com.alexgilleran.icesoap.observer;

import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.request.Request;

public interface SOAPObserver<E> {

	public void onCompletion(Request<E> request);

	public void onException(Request<E> request, SOAPException e);
}
