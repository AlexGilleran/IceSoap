package com.alexgilleran.icesoap.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import com.alexgilleran.icesoap.xpath.elements.XPathElement;

public class AnnotationListParser<T> extends BaseAnnotationParser<List<T>>
		implements ListParser<T> {
	private Parser<T> parser;
	private XPathElement objectXPath;

	public AnnotationListParser(Class<T> clazz) {
		super(retrieveRootXPath(clazz));

		this.parser = new AnnotationParser<T>(clazz);
	}

	public AnnotationListParser(Class<T> clazz, XPathElement containingXPath) {
		this(clazz, containingXPath, new AnnotationParser<T>(clazz,
				containingXPath));
	}

	public AnnotationListParser(Class<T> clazz, XPathElement containingXPath,
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
	public void addItemObserver(ParserObserver<T> observer) {
		parser.addObserver(observer);
	}

	@Override
	public void removeItemObserver(ParserObserver<T> observer) {
		parser.removeObserver(observer);
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
