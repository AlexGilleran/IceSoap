package com.alexgilleran.icesoap.request.impl;

import java.io.InputStream;

import android.os.AsyncTask;

import com.alexgilleran.icesoap.envelope.SOAPEnvelope;
import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.exception.XmlParsingException;
import com.alexgilleran.icesoap.observer.ObserverRegistry;
import com.alexgilleran.icesoap.observer.SOAPObserver;
import com.alexgilleran.icesoap.parser.IceSoapParser;
import com.alexgilleran.icesoap.request.Request;
import com.alexgilleran.icesoap.requester.SOAPRequester;
import com.alexgilleran.icesoap.requester.SOAPRequesterImpl;

/**
 * Implementation of {@link Request}
 * 
 * A {@link RequestImpl}, in essence, is a composition of a {@link SOAPEnvelope}
 * for the request, a {@link IceSoapParser} for the response, and some code to to the
 * post.
 * 
 * @author Alex Gilleran
 * 
 * @param <ResultType>
 */
public class RequestImpl<ResultType> implements Request<ResultType> {
	/** Registry of observers to send events to */
	private ObserverRegistry<ResultType> registry = new ObserverRegistry<ResultType>(
			this);
	/** Parser to use to parse the response */
	private IceSoapParser<ResultType> parser;
	/** The URL to post the request to */
	private String url;
	/** The envelope to serialize and POST */
	private SOAPEnvelope soapEnv;
	/** The {@link AsyncTask} that the request will be performed within */
	private RequestTask<?> currentTask = null;
	/** The result of the request */
	private ResultType result;
	/** Flag - is the request complete? */
	private boolean complete = false;
	/** Flag - is the request currently executing? */
	private boolean executing = false;

	/**
	 * Creates a new request.
	 * 
	 * @param url
	 *            The URL to post the request to
	 * @param parser
	 *            The {@link IceSoapParser} to use to parse the response.
	 * @param soapEnv
	 *            The SOAP envelope to send, as a {@link SOAPEnvelope}
	 */
	public RequestImpl(String url, IceSoapParser<ResultType> parser,
			SOAPEnvelope soapEnv) {
		this.parser = parser;
		this.url = url;
		this.soapEnv = soapEnv;
	}

	/**
	 * Creates a new extension of {@link AsyncTask} to execute.
	 * 
	 * @return The appropriate {@link AsyncTask}
	 */
	protected AsyncTask<Void, ?, ResultType> createTask() {
		return new RequestTask<Void>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void cancel() {
		if (currentTask != null) {
			currentTask.cancel(true);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResultType getResult() {
		return result;
	}

	/**
	 * Uses the {@link SOAPRequester} to post the SOAP request, and get the
	 * response.
	 * 
	 * @return The response, as an {@link InputStream}
	 * @throws SOAPException
	 */
	protected InputStream getResponse() throws SOAPException {
		return SOAPRequesterImpl.getInstance().doSoapRequest(soapEnv, url);
	}

	/**
	 * Gets the parser to use for parsing the response.
	 * 
	 * @return the parser to use.
	 */
	protected IceSoapParser<ResultType> getParser() {
		return parser;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() {
		createTask().execute();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerObserver(SOAPObserver<ResultType> observer) {
		registry.addObserver(observer);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deregisterObserver(SOAPObserver<ResultType> observer) {
		registry.removeObserver(observer);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isExecuting() {
		return executing;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isComplete() {
		return complete;
	}

	/**
	 * Subclass of {@link AsyncTask} used for performing the request in a
	 * background thread.
	 * 
	 * @author Alex Gilleran
	 * 
	 * @param <ProgressReportObject>
	 *            The object passed on progress reports - not used in the base
	 *            class.
	 */
	protected class RequestTask<ProgressReportObject> extends
			AsyncTask<Void, ProgressReportObject, ResultType> {
		/**
		 * If an exception is caught, it is stored here until it can be thrown
		 * on the UI thread
		 */
		private Throwable caughtException = null;

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected void onPostExecute(ResultType returnedResult) {
			executing = true;
			complete = true;

			if (caughtException != null) {
				registry.notifyException(RequestImpl.this, caughtException);
			}

			result = returnedResult;
			registry.notifyComplete(RequestImpl.this);
		}

		/**
		 * Stores an exception that will be thrown on the UI thread.
		 * 
		 * @param exception
		 *            The exception to throw.
		 */
		protected void throwException(Throwable exception) {
			caughtException = exception;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected ResultType doInBackground(Void... arg0) {
			executing = true;

			try {
				if (!isCancelled()) {
					return getParser().parse(getResponse());
				}
			} catch (XmlParsingException e) {
				throwException(e);
			} catch (SOAPException e) {
				throwException(e);
			}

			return null;
		}
	}
}
