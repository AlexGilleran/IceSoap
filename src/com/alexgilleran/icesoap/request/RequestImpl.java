package com.alexgilleran.icesoap.request;

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
import com.alexgilleran.icesoap.requester.SOAPRequesterImpl;

public class RequestImpl<T> implements Request<T> {
	private ObserverRegistry<T> registry = new ObserverRegistry<T>(this);
	private Parser<T> parser;
	private String url;
	private SOAPEnv soapEnv;
	private RequestTask<?> currentTask = null;
	private T result;

	public RequestImpl(String url, Parser<T> aparser, SOAPEnv soapEnv) {
		this.parser = aparser;
		this.url = url;
		this.soapEnv = soapEnv;
	}

	protected AsyncTask<Void, ?, T> createTask() {
		RequestTask<?> currentTask = new RequestTask<Void>();
		return currentTask;
	}

	@Override
	public void cancel() {
		if (currentTask != null) {
			currentTask.cancel(true);
		}
	}

	public T getResult() {
		return result;
	}

	protected InputStream getResponse() throws SOAPException {
		return SOAPRequesterImpl.getInstance().doSoapRequest(soapEnv, url);
	}

	protected Parser<T> getParser() {
		return parser;
	}

	@Override
	public void execute() {
		createTask().execute();
	}

	public void addObserver(SOAPObserver<T> observer) {
		registry.addObserver(observer);
	}

	public void removeObserver(SOAPObserver<T> observer) {
		registry.removeObserver(observer);
	}

	protected class RequestTask<E> extends AsyncTask<Void, E, T> {
		private Throwable caughtException = null;

		@Override
		protected void onPostExecute(T returnedResult) {
			if (caughtException != null) {
				registry.notifyException(RequestImpl.this, caughtException);
			}

			result = returnedResult;
			registry.notifyComplete(RequestImpl.this);
		}

		protected void throwException(Throwable exception) {
			caughtException = exception;
		}

		@Override
		protected T doInBackground(Void... arg0) {
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
