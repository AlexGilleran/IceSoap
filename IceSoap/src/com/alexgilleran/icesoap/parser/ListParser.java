package com.alexgilleran.icesoap.parser;

import java.util.List;


public interface ListParser<T> extends Parser<List<T>> {
	void addItemObserver(ParserObserver<T> observer);

	void removeItemObserver(ParserObserver<T> observer);
}