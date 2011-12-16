package com.alexgilleran.icesoap.request.impl;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParserException;

import android.os.AsyncTask;

import com.alexgilleran.icesoap.envelope.SOAPEnv;
import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.observer.ObserverRegistry;
import com.alexgilleran.icesoap.observer.SOAPObserver;
import com.alexgilleran.icesoap.parser.Parser;
import com.alexgilleran.icesoap.parser.XPathXmlPullParser;
import com.alexgilleran.icesoap.request.Request;
import com.alexgilleran.icesoap.requester.SOAPRequester;
import com.alexgilleran.icesoap.requester.SOAPRequesterImpl;

/**
 * Implementation of {@link Request}
 * 
 * A {@link RequestImpl}, in essence, is a composition of a {@link SOAPEnv} for
 * the request, a {@link Parser} for the response, and some code to to the post.
 * 
 * @author Alex Gilleran
 * 
 * @param <TypeToRetrieve>
 */
public class RequestImpl<TypeToRetrieve> implements Request<TypeToRetrieve> {
	/** Registry of observers to send events to */
	private ObserverRegistry<TypeToRetrieve> registry = new ObserverRegistry<TypeToRetrieve>(
			this);
	/** Parser to use to parse the response */
	private Parser<TypeToRetrieve> parser;
	/** The URL to post the request to */
	private String url;
	/** The envelope to serialize and POST */
	private SOAPEnv soapEnv;
	/** The {@link AsyncTask} that the request will be performed within */
	private RequestTask<?> currentTask = null;
	/** The result of the request */
	private TypeToRetrieve result;
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
	 *            The {@link Parser} to use to parse the response.
	 * @param soapEnv
	 *            The SOAP envelope to send, as a {@link SOAPEnv}
	 */
	public RequestImpl(String url, Parser<TypeToRetrieve> parser,
			SOAPEnv soapEnv) {
		this.parser = parser;
		this.url = url;
		this.soapEnv = soapEnv;
	}

	/**
	 * Creates a new extension of {@link AsyncTask} to execute.
	 * 
	 * @return The appropriate {@link AsyncTask}
	 */
	protected AsyncTask<Void, ?, TypeToRetrieve> createTask() {
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
	public TypeToRetrieve getResult() {
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
	protected Parser<TypeToRetrieve> getParser() {
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
	public void addObserver(SOAPObserver<TypeToRetrieve> observer) {
		registry.addObserver(observer);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeObserver(SOAPObserver<TypeToRetrieve> observer) {
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
			AsyncTask<Void, ProgressReportObject, TypeToRetrieve> {
		/**
		 * If an exception is caught, it is stored here until it can be thrown
		 * on the UI thread
		 */
		private Throwable caughtException = null;

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected void onPostExecute(TypeToRetrieve returnedResult) {
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
		protected TypeToRetrieve doInBackground(Void... arg0) {
			executing = true;

			XPathXmlPullParser xmlParser = new XPathXmlPullParser();

			try {
				if (!isCancelled()) {
					xmlParser.setInput(getResponse(), null);

					return getParser().parse(xmlParser);
				}
			} catch (XmlPullParserException e) {
				throwException(e);
			} catch (SOAPException e) {
				throwException(e);
			} catch (IOException e) {
				throwException(e);
			}

			return null;
		}
	}

}
