package com.alexgilleran.icesoap.request;

import com.alexgilleran.icesoap.observer.SOAPObserver;

/**
 * 
 * 
 * @author Alex Gilleran
 *
 * @param <T>
 */
public interface Request<T> {
	void execute();

	void addObserver(SOAPObserver<T> observer);

	void removeObserver(SOAPObserver<T> observer);

	void cancel();

	T getResult();
}