package com.alexgilleran.icesoap.request;

import java.util.concurrent.ExecutionException;

import com.alexgilleran.icesoap.observer.SOAPObserver;


public interface Request<T> {
	void execute();

	T get() throws ExecutionException;

	void addListener(SOAPObserver<T> listener);

	void removeListener(SOAPObserver<T> listener);

	void cancel();
}