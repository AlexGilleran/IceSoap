package com.alexgilleran.icesoap.observer.registry;

import java.util.ArrayList;
import java.util.List;

import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.observer.SOAPObserver;
import com.alexgilleran.icesoap.request.Request;

/**
 * 
 * 
 * @author Alex Gilleran
 *
 * @param <T>
 */
public class ObserverRegistry<T> {
	private List<SOAPObserver<T>> observers = new ArrayList<SOAPObserver<T>>();
	private Request<T> request;

	public ObserverRegistry(Request<T> request) {
		this.request = request;
	}

	protected Request<T> getRequest() {
		return request;
	}

	public void addObserver(SOAPObserver<T> observer) {
		observers.add(observer);
	}

	public void removeObserver(SOAPObserver<T> observer) {
		observers.remove(observer);
	}

	public void notifyException(Request<T> request, SOAPException exception) {
		for (SOAPObserver<T> observer : observers) {
			observer.onException(request, exception);
		}
	}

	public void notifyComplete(Request<T> request) {
		for (SOAPObserver<T> observer : observers) {
			observer.onCompletion(request);
		}
	}
}
