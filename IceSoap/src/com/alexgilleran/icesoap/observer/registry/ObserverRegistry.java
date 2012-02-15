package com.alexgilleran.icesoap.observer.registry;

import java.util.ArrayList;
import java.util.List;

import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.observer.BaseSOAPObserver;
import com.alexgilleran.icesoap.observer.SOAPObserver;
import com.alexgilleran.icesoap.request.BaseRequest;

/**
 * Helper class that holds a collection of {@link SOAPObserver}s and allows them
 * all to be notified of events with one call.
 * 
 * @author Alex Gilleran
 * 
 * @param <TypeToReturn>
 *            The type of the object that will be returned from the SOAP call.
 */
public class ObserverRegistry<TypeToReturn, SOAPFaultType> {
	/** The observers that will be notified of new events */
	private List<BaseSOAPObserver<TypeToReturn, SOAPFaultType>> observers = new ArrayList<BaseSOAPObserver<TypeToReturn, SOAPFaultType>>();

	/**
	 * Registers an observer - when any notify events are called, this will be
	 * one of the observers called.
	 * 
	 * @param observer
	 *            the observer to add.
	 */
	public void registerObserver(
			BaseSOAPObserver<TypeToReturn, SOAPFaultType> observer) {
		observers.add(observer);
	}

	/**
	 * Deregisters an observer - it will now longer receive calls.
	 * 
	 * @param observer
	 *            The observer to deregister.
	 */
	public void deregisterObserver(
			BaseSOAPObserver<TypeToReturn, SOAPFaultType> observer) {
		observers.remove(observer);
	}

	/**
	 * Notifies all observers of a {@link BaseRequest} of an {@link Exception} that
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
	public void notifyException(BaseRequest<TypeToReturn, SOAPFaultType> request,
			SOAPException exception) {
		for (BaseSOAPObserver<TypeToReturn, SOAPFaultType> observer : observers) {
			observer.onException(request, exception);
		}
	}

	/**
	 * Notifies all observers that a request is complete.
	 * 
	 * @param request
	 *            The request that's completed.
	 */
	public void notifyComplete(BaseRequest<TypeToReturn, SOAPFaultType> request) {
		for (BaseSOAPObserver<TypeToReturn, SOAPFaultType> observer : observers) {
			observer.onCompletion(request);
		}
	}
}
