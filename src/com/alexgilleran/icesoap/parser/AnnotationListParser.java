package com.alexgilleran.icesoap.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;


import com.alexgilleran.icesoap.observer.SOAPObserver;
import com.alexgilleran.icesoap.xpath.XPath;

public class AnnotationListParser<T> extends BaseAnnotationParser<List<T>>
		implements ListParser<T> {
	private Parser<T> parser;
	private XPath objectXPath;

	public AnnotationListParser(Class<T> clazz) {
		super(clazz);

		this.parser = new AnnotationParser<T>(clazz);
	}

	public AnnotationListParser(Class<T> clazz, XPath containingXPath) {
		this(clazz, containingXPath, new AnnotationParser<T>(clazz,
				containingXPath));
	}

	public AnnotationListParser(Class<T> clazz, XPath containingXPath,
			Parser<T> parser) {
		super(containingXPath);

		objectXPath = super.retrieveRootXPath(clazz);

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
		if (objectXPath == null
				|| objectXPath.matches(xmlPullParser.getCurrentElement())) {
			T object = parser.parse(xmlPullParser);

			if (object != null) {
				listSoFar.add(object);
			}
		}

		return listSoFar;
	}

}
