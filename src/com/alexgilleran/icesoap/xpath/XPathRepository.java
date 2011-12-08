package com.alexgilleran.icesoap.xpath;

import java.util.ArrayList;
import java.util.List;

// TODO: Do this more cleverly and quicker.
public class XPathRepository<T> {
	private List<XPathPair> list = new ArrayList<XPathPair>();

	public void put(XPath key, T value) {
		list.add(new XPathPair(key, value));
	}

	public boolean containsKey(XPath xPath) {
		return (get(xPath) != null);
	}

	public T get(XPath xpath) {
		T returnItem = null;

		for (XPathPair item : list) {
			if (item.getXPath().matches(xpath)) {
				returnItem = item.getValue();
			}
		}

		return returnItem;
	}

	private class XPathPair {
		private XPath xpath;
		private T value;

		public XPathPair(XPath key, T value) {
			this.xpath = key;
			this.value = value;
		}

		public XPath getXPath() {
			return xpath;
		}

		public T getValue() {
			return value;
		}
	}
}
