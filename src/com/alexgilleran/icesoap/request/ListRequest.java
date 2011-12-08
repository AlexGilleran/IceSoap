package com.alexgilleran.icesoap.request;

import java.util.List;

import com.alexgilleran.icesoap.observer.SOAPObserver;

public interface ListRequest<T> extends Request<List<T>> {
	void addItemListener(SOAPObserver<T> parserListener);

	void removeItemListener(SOAPObserver<T> parserListener);
}
