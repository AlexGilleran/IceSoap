package com.alexgilleran.icesoap.request;

import java.util.List;

import com.alexgilleran.icesoap.observer.SOAPListObserver;

/**
 * 
 * 
 * @author Alex Gilleran
 *
 * @param <T>
 */
public interface ListRequest<T> extends Request<List<T>> {
	void addItemObserver(SOAPListObserver<T> observer);

	void removeItemObserver(SOAPListObserver<T> observer);
}
