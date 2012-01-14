/**
 * 
 */
package com.alexgilleran.icesoap.observer.registry;

import java.util.ArrayList;
import java.util.List;

import com.alexgilleran.icesoap.observer.SOAPListObserver;
import com.alexgilleran.icesoap.observer.SOAPObserver;
import com.alexgilleran.icesoap.request.ListRequest;
import com.alexgilleran.icesoap.request.Request;

/**
 * Extension of {@link ObserverRegistry} to deal with {@link ListRequest}s.
 * Contains extra methods to deal specifically with individual items in a list.
 * 
 * @author Alex Gilleran
 * 
 */
public class ListObserverRegistry<T> extends ObserverRegistry<List<T>> {
	/** A list of observers that are specifically list-based */
	private List<SOAPListObserver<T>> listObservers = new ArrayList<SOAPListObserver<T>>();

	/**
	 * Adds a list observer. Node that this observer will receive all events on
	 * the {@link SOAPListObserver} interface, rather than just those on the
	 * {@link SOAPObserver}.
	 * 
	 * @param observer
	 *            The observer to add.
	 */
	public void registerObserver(SOAPListObserver<T> observer) {
		super.registerObserver(observer);

		listObservers.add(observer);
	}

	/**
	 * Removes an observer.
	 * 
	 * @param observer
	 *            The observer to remove.
	 */
	public void deregisterObserver(SOAPListObserver<T> observer) {
		super.deregisterObserver(observer);

		listObservers.remove(observer);
	}

	/**
	 * Notifies observers of a new item that's been discovered.
	 * 
	 * @param request
	 *            The request that has parsed the new item.
	 * @param item
	 *            The item itself.
	 */
	public void notifyNewItem(Request<List<T>> request, T item) {
		for (SOAPListObserver<T> observer : listObservers) {
			observer.onNewItem(request, item);
		}
	}
}
