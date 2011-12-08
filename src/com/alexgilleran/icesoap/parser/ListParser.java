package com.alexgilleran.icesoap.parser;

import java.util.List;

import com.alexgilleran.icesoap.observer.SOAPObserver;

public interface ListParser<T> extends Parser<List<T>> {

	void addItemListener(SOAPObserver<T> listener);

	void removeItemListener(SOAPObserver<T> listener);
}