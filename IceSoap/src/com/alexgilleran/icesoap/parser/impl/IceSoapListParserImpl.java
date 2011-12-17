package com.alexgilleran.icesoap.parser.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.alexgilleran.icesoap.exception.XmlParsingException;
import com.alexgilleran.icesoap.parser.ItemObserver;
import com.alexgilleran.icesoap.parser.IceSoapListParser;
import com.alexgilleran.icesoap.parser.XPathPullParser;
import com.alexgilleran.icesoap.xpath.elements.XPathElement;

public class IceSoapListParserImpl<T> extends BaseIceSoapParserImpl<List<T>>
		implements IceSoapListParser<T> {
	private BaseIceSoapParserImpl<T> parser;
	private XPathElement objectXPath;
	private Set<ItemObserver<T>> observers = new HashSet<ItemObserver<T>>();

	public IceSoapListParserImpl(Class<T> clazz) {
		super(retrieveRootXPath(clazz));

		this.parser = new IceSoapImpl<T>(clazz);
	}

	public IceSoapListParserImpl(Class<T> clazz, XPathElement containingXPath) {
		this(clazz, containingXPath, new IceSoapImpl<T>(clazz, containingXPath));
	}

	protected IceSoapListParserImpl(Class<T> clazz,
			XPathElement containingXPath, BaseIceSoapParserImpl<T> parser) {
		super(containingXPath);

		objectXPath = super.retrieveRootXPath(clazz);

		this.parser = parser;
	}

	public void registerItemObserver(ItemObserver<T> observer) {
		observers.add(observer);
	}

	public void deregisterItemObserver(ItemObserver<T> observer) {
		observers.remove(observer);
	}

	private void notifyObservers(T newItem) {
		for (ItemObserver<T> observer : observers) {
			observer.onNewItem(newItem);
		}
	}

	public List<T> initializeParsedObject() {
		return new ArrayList<T>();
	}

	@Override
	protected List<T> onNewTag(XPathPullParser xmlPullParser, List<T> listSoFar)
			throws XmlParsingException {
		if (objectXPath == null
				|| objectXPath.matches(xmlPullParser.getCurrentElement())) {
			T object = parser.parse(xmlPullParser);

			if (object != null) {
				listSoFar.add(object);
				notifyObservers(object);
			}
		}

		return listSoFar;
	}

}
