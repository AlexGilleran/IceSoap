package com.alexgilleran.icesoap.xpath;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.alexgilleran.icesoap.xpath.elements.NodeXPElement;
import com.alexgilleran.icesoap.xpath.elements.SingleSlashXPElement;

public class XPathRepository<T> {
	private Map<String, Set<SingleSlashXPElement>> lookupMap = new HashMap<String, Set<SingleSlashXPElement>>();
	private Map<SingleSlashXPElement, T> valueMap = new HashMap<SingleSlashXPElement, T>();

	public void put(SingleSlashXPElement element, T value) {
		valueMap.put(element, value);
		
		Set<SingleSlashXPElement> existingSet = lookupMap.get(element.getName());

		if (existingSet == null) {
			lookupMap.put(element.getName(), newElementSet(element));
		} else {
			existingSet.add(element);
		}
	}

	private Set<SingleSlashXPElement> newElementSet(SingleSlashXPElement element) {
		Set<SingleSlashXPElement> elementSet = new HashSet<SingleSlashXPElement>();
		elementSet.add(element);
		return elementSet;
	}

	public T get(NodeXPElement endElement) {
		Set<SingleSlashXPElement> possibleElements = lookupMap.get(endElement
				.getName());

		if (possibleElements != null) {
			for (NodeXPElement possElement : possibleElements) {
				if (possElement.matches(endElement)) {
					return valueMap.get(endElement);
				}
			}
		}

		return null;
	}
}
