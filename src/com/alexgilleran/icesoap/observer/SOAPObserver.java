package com.alexgilleran.icesoap.observer;

import com.alexgilleran.icesoap.request.Request;

public interface SOAPObserver<E> {

	public void onException(Request<E> request, Throwable e);

	public void onCompletion(Request<E> request);
}
