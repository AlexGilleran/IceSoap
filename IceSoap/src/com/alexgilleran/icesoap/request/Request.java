package com.alexgilleran.icesoap.request;

import java.util.List;

import android.os.AsyncTask;

import com.alexgilleran.icesoap.observer.SOAPObserver;

/**
 * Encapsulates all the code for making a SOAP Request - to use, create an
 * object that implements the {@link SOAPObserver} class (generally this is
 * easiest using anonymous objects from the activity or fragment) and attach it
 * using the {@link Request#registerObserver(SOAPObserver)} method. Then invoke
 * {@link Request#execute()} - the request will be performed on a background
 * thread, and methods of the {@link SOAPObserver} will be called on the UI
 * thread.
 * 
 * @author Alex Gilleran
 * 
 * @param <ResultType>
 *            The type of the object to retrieve from this request. If the type
 *            is a {@link List}, you may want to consider using
 *            {@link ListRequest} instead.
 */
public interface Request<ResultType> {
	/**
	 * Executes the request
	 */
	void execute();

	/**
	 * Adds an observer to the request - the observer's methods will be called
	 * on certain events.
	 * 
	 * @param observer
	 *            The observer to add.
	 */
	void registerObserver(SOAPObserver<ResultType> observer);

	/**
	 * Remove an observer
	 * 
	 * @param observer
	 *            The observer to remove.
	 */
	void deregisterObserver(SOAPObserver<ResultType> observer);

	/**
	 * Cancels the request - akin to cancelling an {@link AsyncTask}
	 */
	void cancel();

	/**
	 * Gets the result of the request - if the request is still running, gets
	 * the result so far.
	 * 
	 * @return The result so far.
	 */
	ResultType getResult();

	/**
	 * Whether the request is currently executing - if it's not executing, it
	 * might be stopped or it might not have started yet.
	 * 
	 * @return true if executing, otherwise false.
	 */
	boolean isExecuting();

	/**
	 * Reveals whether the request is complete yet.
	 * 
	 * @return true if complete, otherwise false.
	 */
	boolean isComplete();
}