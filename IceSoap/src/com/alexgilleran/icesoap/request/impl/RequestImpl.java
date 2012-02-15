package com.alexgilleran.icesoap.request.impl;

import java.io.InputStream;

import android.os.AsyncTask;

import com.alexgilleran.icesoap.envelope.SOAPEnvelope;
import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.exception.XMLParsingException;
import com.alexgilleran.icesoap.observer.BaseSOAPObserver;
import com.alexgilleran.icesoap.observer.registry.ObserverRegistry;
import com.alexgilleran.icesoap.parser.IceSoapParser;
import com.alexgilleran.icesoap.parser.impl.IceSoapParserImpl;
import com.alexgilleran.icesoap.request.Request;
import com.alexgilleran.icesoap.request.SOAPRequester;

/**
 * Implementation of {@link Request}
 * 
 * A {@link RequestImpl}, in essence, is a composition of a {@link SOAPEnvelope}
 * for the request, a {@link IceSoapParser} for the response, and some code to
 * to the post.
 * 
 * @author Alex Gilleran
 * 
 * @param <ResultType>
 */
public class RequestImpl<ResultType, SOAPFaultType> implements
		Request<ResultType, SOAPFaultType> {
	/** Registry of observers to send events to */
	private ObserverRegistry<ResultType, SOAPFaultType> registry = new ObserverRegistry<ResultType, SOAPFaultType>();
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
	/** Class to perform SOAP requests */
	private SOAPRequester soapRequester;
	/** The SOAPAction to perform */
	private String soapAction;
	/** A SOAPFaultType, if one has been encountered */
	private SOAPFaultType soapFault;

	/**
	 * If an exception is caught, it is stored here until it can be thrown on
	 * the UI thread
	 */
	private SOAPException caughtException = null;

	/**
	 * Creates a new request, automatically creating the parser.
	 * 
	 * @param url
	 *            The URL to post the request to
	 * @param soapEnv
	 *            The SOAP envelope to send, as a {@link SOAPEnvelope}
	 * @param soapAction
	 *            The SOAP Action to pass in the HTTP header - can be null
	 * @param resultClass
	 *            The class of the type to return from the request.
	 * @param requester
	 *            The implementation of {@link SOAPRequester} to use for
	 *            requests.
	 */
	protected RequestImpl(String url, SOAPEnvelope soapEnv, String soapAction,
			Class<ResultType> resultClass, SOAPRequester requester) {
		this(url, soapEnv, soapAction, new IceSoapParserImpl<ResultType>(
				resultClass), requester);
	}

	/**
	 * Creates a new request.
	 * 
	 * @param url
	 *            The URL to post the request to
	 * @param soapEnv
	 *            The SOAP envelope to send, as a {@link SOAPEnvelope}
	 * @param soapAction
	 *            The SOAP Action to pass in the HTTP header - can be null
	 * @param parser
	 *            The {@link IceSoapParser} to use to parse the response.
	 * @param requester
	 *            The implementation of {@link SOAPRequester} to use for
	 *            requests.
	 */
	protected RequestImpl(String url, SOAPEnvelope soapEnv, String soapAction,
			IceSoapParser<ResultType> parser, SOAPRequester requester) {
		this.parser = parser;
		this.url = url;
		this.soapEnv = soapEnv;
		this.soapAction = soapAction;
		this.soapRequester = requester;
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
		return soapRequester.doSoapRequest(soapEnv, url, soapAction).getData();
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
	public void execute(BaseSOAPObserver<ResultType, SOAPFaultType> observer) {
		registerObserver(observer);
		execute();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerObserver(
			BaseSOAPObserver<ResultType, SOAPFaultType> observer) {
		registry.registerObserver(observer);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deregisterObserver(
			BaseSOAPObserver<ResultType, SOAPFaultType> observer) {
		registry.deregisterObserver(observer);
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
	 * {@inheritDoc}
	 */
	@Override
	public Throwable getException() {
		return caughtException;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SOAPFaultType getSOAPFault() {
		return soapFault;
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
		 * {@inheritDoc}
		 */
		@Override
		protected void onPostExecute(ResultType returnedResult) {
			complete = true;
			executing = false;

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
		protected void throwException(SOAPException exception) {
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
			} catch (XMLParsingException e) {
				throwException(new SOAPException(e));
			} catch (SOAPException e) {
				throwException(e);
			}

			return null;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((caughtException == null) ? 0 : caughtException.hashCode());
		result = prime * result + (complete ? 1231 : 1237);
		result = prime * result
				+ ((currentTask == null) ? 0 : currentTask.hashCode());
		result = prime * result + (executing ? 1231 : 1237);
		result = prime * result + ((parser == null) ? 0 : parser.hashCode());
		result = prime * result
				+ ((registry == null) ? 0 : registry.hashCode());
		result = prime * result
				+ ((this.result == null) ? 0 : this.result.hashCode());
		result = prime * result + ((soapEnv == null) ? 0 : soapEnv.hashCode());
		result = prime * result
				+ ((soapRequester == null) ? 0 : soapRequester.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RequestImpl<?, ?> other = (RequestImpl<?, ?>) obj;
		if (caughtException == null) {
			if (other.caughtException != null)
				return false;
		} else if (!caughtException.equals(other.caughtException))
			return false;
		if (complete != other.complete)
			return false;
		if (currentTask == null) {
			if (other.currentTask != null)
				return false;
		} else if (!currentTask.equals(other.currentTask))
			return false;
		if (executing != other.executing)
			return false;
		if (parser == null) {
			if (other.parser != null)
				return false;
		} else if (!parser.equals(other.parser))
			return false;
		if (registry == null) {
			if (other.registry != null)
				return false;
		} else if (!registry.equals(other.registry))
			return false;
		if (result == null) {
			if (other.result != null)
				return false;
		} else if (!result.equals(other.result))
			return false;
		if (soapEnv == null) {
			if (other.soapEnv != null)
				return false;
		} else if (!soapEnv.equals(other.soapEnv))
			return false;
		if (soapRequester == null) {
			if (other.soapRequester != null)
				return false;
		} else if (!soapRequester.equals(other.soapRequester))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
}
