package com.alexgilleran.icesoap.observer.registry;

import java.util.ArrayList;
import java.util.List;

import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.observer.SOAPObserver;
import com.alexgilleran.icesoap.request.Request;

/**
 * Helper class that holds a collection of {@link SOAPObserver}s and allows them
 * all to be notified of events with one call.
 * 
 * @author Alex Gilleran
 * 
 * @param <TypeToReturn>
 *            The type of the object that will be returned from the SOAP call.
 */
public class ObserverRegistry<TypeToReturn> {
	/** The observers that will be notified of new events */
	private List<SOAPObserver<TypeToReturn>> observers = new ArrayList<SOAPObserver<TypeToReturn>>();

	/**
	 * Registers an observer - when any notify events are called, this will be
	 * one of the observers called.
	 * 
	 * @param observer
	 *            the observer to add.
	 */
	public void registerObserver(SOAPObserver<TypeToReturn> observer) {
		observers.add(observer);
	}

	/**
	 * Deregisters an observer - it will now longer receive calls.
	 * 
	 * @param observer
	 *            The observer to deregister.
	 */
	public void deregisterObserver(SOAPObserver<TypeToReturn> observer) {
		observers.remove(observer);
	}

	/**
	 * Notifies all observers of a {@link Request} of an {@link Exception} that
	 * has occurred during request processing.
	 * 
	 * This is necessary due to the complications of passing checked exceptions
	 * that happen on a background thread.
	 * 
	 * @param request
	 *            The request that's hit an exception.
	 * @param exception
	 *            The exception that has occurred.
	 */
	public void notifyException(Request<TypeToReturn> request,
			SOAPException exception) {
		for (SOAPObserver<TypeToReturn> observer : observers) {
			observer.onException(request, exception);
		}
	}

	/**
	 * Notifies all observers that a request is complete.
	 * 
	 * @param request
	 *            The request that's completed.
	 */
	public void notifyComplete(Request<TypeToReturn> request) {
		for (SOAPObserver<TypeToReturn> observer : observers) {
			observer.onCompletion(request);
		}
	}
}
