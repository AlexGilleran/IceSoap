package com.alexgilleran.icesoap.xpath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO: Do this more cleverly and quicker.
public class XPathRepository<T> {
	private Map<XPath, List<XPathPair>> map = new HashMap<XPath, List<XPathPair>>();

	public void put(XPath key, T value) {
		XPath keyWithoutPredicates = key.getWithoutPredicates();

		if (!map.containsKey(keyWithoutPredicates)) {
			map.put(keyWithoutPredicates, new ArrayList<XPathPair>());
		}

		map.get(keyWithoutPredicates).add(new XPathPair(key, value));
	}

	public T get(XPath xpath) {
		List<XPathPair> list = map.get(xpath.getWithoutPredicates());

		if (list != null) {
			for (XPathPair item : list) {
				if (item.getXPath().matches(xpath)) {
					return item.getValue();
				}
			}
		}

		return null;
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
