/**
 * 
 */
package com.alexgilleran.icesoap.observer.registry;

import java.util.ArrayList;
import java.util.List;

import com.alexgilleran.icesoap.observer.SOAPListObserver;
import com.alexgilleran.icesoap.observer.SOAP11ListObserver;
import com.alexgilleran.icesoap.observer.SOAP11Observer;
import com.alexgilleran.icesoap.request.ListRequest;
import com.alexgilleran.icesoap.request.Request;

/**
 * Extension of {@link ObserverRegistry} to deal with {@link ListRequest}s.
 * Contains extra methods to deal specifically with individual items in a list.
 * 
 * @author Alex Gilleran
 * 
 */
public class ListObserverRegistry<T, SOAPFaultType> extends
		ObserverRegistry<List<T>, SOAPFaultType> {
	/** A list of observers that are specifically list-based */
	private List<SOAPListObserver<T, SOAPFaultType>> listObservers = new ArrayList<SOAPListObserver<T, SOAPFaultType>>();

	/**
	 * Adds a list observer. Node that this observer will receive all events on
	 * the {@link SOAP11ListObserver} interface, rather than just those on the
	 * {@link SOAP11Observer}.
	 * 
	 * @param observer
	 *            The observer to add.
	 */
	public void registerObserver(SOAPListObserver<T, SOAPFaultType> observer) {
		super.registerObserver(observer);

		listObservers.add(observer);
	}

	/**
	 * Removes an observer.
	 * 
	 * @param observer
	 *            The observer to remove.
	 */
	public void deregisterObserver(
			SOAPListObserver<T, SOAPFaultType> observer) {
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
	public void notifyNewItem(Request<List<T>, SOAPFaultType> request, T item) {
		for (SOAPListObserver<T, SOAPFaultType> observer : listObservers) {
			observer.onNewItem(request, item);
		}
	}
}
