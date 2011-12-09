package com.alexgilleran.icesoap.request;

import java.util.List;

import com.alexgilleran.icesoap.envelope.SOAPEnv;
import com.alexgilleran.icesoap.observer.ObserverRegistry;
import com.alexgilleran.icesoap.observer.SOAPObserver;
import com.alexgilleran.icesoap.parser.ListParser;

import android.os.AsyncTask;
import android.util.Log;


public class ListRequestImpl<T> extends RequestImpl<List<T>> implements
		ListRequest<T> {
	private ListParser<T> parser;
	private ObserverRegistry<T> itemRegistry = new ObserverRegistry<T>();

	public ListRequestImpl(String url, ListParser<T> parser, SOAPEnv soapEnv) {
		super(url, parser, soapEnv);

		this.parser = parser;
	}

	@Override
	public void addItemListener(SOAPObserver<T> listener) {
		itemRegistry.addListener(listener);
	}

	@Override
	public void removeItemListener(SOAPObserver<T> listener) {
		itemRegistry.removeListener(listener);
	}

	@Override
	protected AsyncTask<Void, T, List<T>> createTask() {
		return new ListRequestTask();
	}

	private class ListRequestTask extends RequestTask<T> {	
		@Override
		protected void onPreExecute() {
			Log.d("debug", "onPreExecute executed");
			parser.addItemListener(itemListener);
		}

		@Override
		protected void onProgressUpdate(T... item) {
			itemRegistry.notifyListeners(item[0]);
		}

		private SOAPObserver<T> itemListener = new SOAPObserver<T>() {
			@Override
			public void onNewDaoItem(T item) {
				publishProgress(item);
			}
		};
	}
}
