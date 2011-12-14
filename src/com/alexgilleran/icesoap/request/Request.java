package com.alexgilleran.icesoap.request;

import com.alexgilleran.icesoap.observer.SOAPObserver;

public interface Request<T> {
	void execute();

	void addObserver(SOAPObserver<T> observer);

	void removeObserver(SOAPObserver<T> observer);

	void cancel();

	T getResult();
}