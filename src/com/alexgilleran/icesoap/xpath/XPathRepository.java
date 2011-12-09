package com.alexgilleran.icesoap.xpath;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class XPathRepository<T> {
	private Map<BasicXPathElement, Set<XPathElement>> lookupMap = new HashMap<BasicXPathElement, Set<XPathElement>>();
	private Map<XPathElement, T> valueMap = new HashMap<XPathElement, T>();

	public void put(XPathElement element) {
		Set<XPathElement> existingSet = lookupMap.get(element.getBasic());

		if (existingSet == null) {
			lookupMap.put(element.getBasic(), newElementSet(element));
		} else {
			existingSet.add(element);
		}
	}

	private Set<XPathElement> newElementSet(XPathElement element) {
		Set<XPathElement> elementSet = new HashSet<XPathElement>();
		elementSet.add(element);
		return elementSet;
	}

	public T get(XPathElement endElement) {
		Set<XPathElement> possibleElements = lookupMap.get(endElement
				.getBasic());

		if (possibleElements != null) {
			for (XPathElement possElement : possibleElements) {
				if (possElement.matches(endElement)) {
					return valueMap.get(endElement);
				}
			}
		}

		return null;
	}
}
