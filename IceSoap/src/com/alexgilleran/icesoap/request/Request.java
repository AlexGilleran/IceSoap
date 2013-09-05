package com.alexgilleran.icesoap.request;

import java.util.List;
import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;

import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.observer.SOAPObserver;
import com.alexgilleran.icesoap.observer.SOAP11Observer;

/**
 * Encapsulates all the code for making a SOAP Request - to use, create an
 * object that implements the {@link SOAP11Observer} class (generally this is
 * easiest using anonymous objects from the activity or fragment) and attach it
 * using the {@link Request#registerObserver(SOAP11Observer)} method. Then
 * invoke {@link Request#execute()} - the request will be performed on a
 * background thread, and methods of the {@link SOAP11Observer} will be called
 * on the UI thread.
 * 
 * @author Alex Gilleran
 * 
 * @param <ResultType>
 *            The type of the object to retrieve from this request. If the type
 *            is a {@link List}, you may want to consider using
 *            {@link ListRequest} instead.
 * @param <SOAPFaultType>
 *            The type of the fault object that will be parsed from the
 *            response, should an HTTP Error 500 be encountered.
 */
public interface Request<ResultType, SOAPFaultType> {
	/**
	 * Executes the request.
	 */
	void execute();

	/**
	 * Registers the provided observer then executes the request - this is
	 * equivalent to calling {@link #registerObserver(SOAP11Observer)}, then
	 * {@link #execute()}.
	 * 
	 * @param observer
	 *            An observer to register
	 */
	void execute(SOAPObserver<ResultType, SOAPFaultType> observer);

	/**
	 * Executes the request on the same thread it's called on. This will not
	 * result in registered {@link SOAPObserver}s being called.
	 * 
	 * <p>
	 * Note: Only use this method when you're already on a background thread
	 * e.g. in a service. Using this from the main U.I. thread will at best lock
	 * the screen for a while, and in newer versions of Android will result in
	 * exceptions being thrown.
	 * 
	 * @return The result of this request.
	 * @throws SOAPException
	 *             If an exception is thrown while executing.
	 */
	ResultType executeBlocking() throws SOAPException;

	/**
	 * Adds an observer to the request - the observer's methods will be called
	 * on certain events.
	 * 
	 * @param observer
	 *            The observer to add.
	 */
	void registerObserver(SOAPObserver<ResultType, SOAPFaultType> observer);

	/**
	 * Remove an observer.
	 * 
	 * @param observer
	 *            The observer to remove.
	 */
	void deregisterObserver(SOAPObserver<ResultType, SOAPFaultType> observer);

	/**
	 * Cancels the request - akin to cancelling an {@link AsyncTask}.
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

	/**
	 * Retrieves an exception if one has been encountered.
	 * 
	 * @return The encountered exception if one exists, otherwise null.
	 */
	Throwable getException();

	/**
	 * Gets the SOAP Fault that has been encountered, if one has been
	 * encountered.
	 * 
	 * @return the SOAP Fault.
	 */
	SOAPFaultType getSOAPFault();

	/**
	 * Activates debug mode for this request - debug mode results in the request
	 * storing the request and response as a String
	 * 
	 * @param activated
	 *            Whether debug mode is activated.
	 */
	void setDebugMode(boolean activated);

	/**
	 * Gets the request XML as a string, if debug mode has been activated with
	 * {@link #setDebugMode(boolean)}.
	 * 
	 * @return The request XML as a string.
	 */
	String getRequestXML();

	/**
	 * Gets the response XML as a string, if debug mode has been activated with
	 * {@link #setDebugMode(boolean)}
	 * 
	 * @return The response XML as a string.
	 */
	String getResponseXML();
}