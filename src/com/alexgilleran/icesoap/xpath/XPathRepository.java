package com.alexgilleran.icesoap.xpath;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.alexgilleran.icesoap.xpath.elements.XPathElement;

public class XPathRepository<T> {
	private Map<String, Set<XPathElement>> lookupMap = new HashMap<String, Set<XPathElement>>();
	private Map<XPathElement, T> valueMap = new HashMap<XPathElement, T>();

	public void put(XPathElement element, T value) {
		valueMap.put(element, value);
		
		Set<XPathElement> existingSet = lookupMap.get(element.getName());

		if (existingSet == null) {
			lookupMap.put(element.getName(), newElementSet(element));
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
				.getName());

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
