package com.alexgilleran.icesoap.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import com.alexgilleran.icesoap.observer.SOAPObserver;

public class AnnotationListParser<T> extends BaseAnnotationParser<List<T>>
		implements ListParser<T> {
	private Parser<T> parser;

	public AnnotationListParser(Class<T> clazz) {
		this(clazz, new AnnotationParser<T>(clazz));
	}

	public AnnotationListParser(Class<T> clazz, Parser<T> parser) {
		super(clazz);

		this.parser = parser;
	}

	@Override
	public List<T> initializeParsedObject() {
		return new ArrayList<T>();
	}

	@Override
	public void addItemListener(SOAPObserver<T> listener) {
		parser.addListener(listener);
	}

	@Override
	public void removeItemListener(SOAPObserver<T> listener) {
		parser.removeListener(listener);
	}

	@Override
	protected List<T> onNewTag(XPathXmlPullParser xmlPullParser,
			List<T> listSoFar) throws XmlPullParserException, IOException {
		T object = parser.parse(xmlPullParser);

		if (object != null) {
			listSoFar.add(object);
		}

		return listSoFar;
	}

}
