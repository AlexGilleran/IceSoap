package com.alexgilleran.icesoap.request;

import java.util.List;

import com.alexgilleran.icesoap.observer.SOAPListObserver;
import com.alexgilleran.icesoap.observer.SOAPObserver;

/**
 * {@inheritDoc}
 * 
 * This request deals specifically with SOAP calls that recall a list of items,
 * allowing listeners to be bound that will be called every time a new object is
 * parsed rather than waiting until the entire request is finished.
 * 
 * @author Alex Gilleran
 * 
 * @param <ResultType>
 *            The type of the object that this request will return a list of.
 */
public interface ListRequest<ResultType> extends Request<List<ResultType>> {
	/**
	 * Registers an observer for new item, exception and completion events.
	 * 
	 * Note that registering a {@link SOAPListObserver} (using this method) will
	 * result in the observer receiving events on each new item - registering a
	 * {@link SOAPObserver} with {@link Request#registerObserver(SOAPObserver)}
	 * will only result in it receiving exception and completion.
	 * 
	 * @param observer
	 *            The observer to register.
	 */
	void registerObserver(SOAPListObserver<ResultType> observer);

	/**
	 * De-registers an observer
	 * 
	 * @param observer
	 *            The observer to deregister.
	 */
	void deregisterObserver(SOAPListObserver<ResultType> observer);
}
