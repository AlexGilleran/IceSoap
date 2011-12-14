/**
 * 
 */
package com.alexgilleran.icesoap.observer;

import java.util.ArrayList;
import java.util.List;

import com.alexgilleran.icesoap.request.Request;

/**
 * @author Alex Gilleran
 * 
 */
public class ListObserverRegistry<T> extends ObserverRegistry<List<T>> {
	private List<SOAPListObserver<T>> listObservers = new ArrayList<SOAPListObserver<T>>();

	public ListObserverRegistry(Request<List<T>> request) {
		super(request);
	}

	public void addObserver(SOAPListObserver<T> observer) {
		super.addObserver(observer);

		listObservers.add(observer);
	}

	public void removeObserver(SOAPListObserver<T> observer) {
		super.removeObserver(observer);

		listObservers.remove(observer);
	}

	public void notifyNewItem(Request<List<T>> request, T item) {
		for (SOAPListObserver<T> observer : listObservers) {
			observer.onNewItem(getRequest(), item);
		}
	}

}
