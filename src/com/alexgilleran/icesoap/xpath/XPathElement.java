package com.alexgilleran.icesoap.xpath;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class XPathElement {
	private BasicXPathElement basicElement;
	private XPathElement previousElement;
	private boolean startsWithDoubleSlash;
	private Map<String, String> predicates = new HashMap<String, String>();

	// private boolean

	public XPathElement(String name, boolean isAllNode,
			XPathElement previousElement) {
		basicElement = new BasicXPathElement(name);
	}

	public BasicXPathElement getBasic() {
		return basicElement;
	}

	public void addPredicate(String name, String value) {
		predicates.put(name, value);
	}

	public boolean matches(XPathElement otherElement) {
		// TODO: This is procedural (and bad) - make it more OO

		// Explanation: Basically this goes through and finds any reason that
		// two xpaths *won't* match, in order of most likely reason to least
		// likely - if none of these reasons are present, returns true

		if (!this.basicElement.equals(otherElement.basicElement)) {
			return false;
		}

		for (String predicateKey : predicates.keySet()) {
			if (!otherElement.predicates.containsKey(predicateKey)) {
				return false;
			}

			if (!this.predicates.get(predicateKey).equals(
					otherElement.predicates.get(predicateKey))) {
				return false;
			}
		}

		if (this.isFirstElement()) {
			if (!startsWithDoubleSlash && !otherElement.isFirstElement()) {
				// This element is the beginning element of a non-doubleslash
				// xpath, but the other xpath has previous elements still.
				return false;
			}
		} else {
			if (startsWithDoubleSlash) {
				// This element starts with '//' and has previous elements -
				// loop through all the previous elements that the other element
				// has, to see if any of them match this node's previous element
				XPathElement thisPrevElement = otherElement.previousElement;

				while (thisPrevElement != null) {
					if (thisPrevElement.matches(otherElement.previousElement)) {
						return true;
					} else {
						thisPrevElement = thisPrevElement.previousElement;
					}
				}

				return false;
			} else {
				// Previous element is not null and this isn't a '//' element -
				// see if the previous elements of both xpaths match
				return previousElement.matches(otherElement.previousElement);
			}
		}

		return true;
	}

	public boolean isFirstElement() {
		return previousElement == null;
	}

	public StringBuilder toStringBuilder() {
		StringBuilder builder;

		// If this isn't the first element, the string representation of this
		// will build on that of previous elements in the whole xpath
		if (isFirstElement()) {
			builder = new StringBuilder();
		} else {
			builder = previousElement.toStringBuilder();
		}

		if (startsWithDoubleSlash) {
			builder.append("//");
		} else {
			builder.append("/");
		}

		builder.append(basicElement.getName());

		if (!predicates.isEmpty()) {
			builder.append("[");

			Iterator<String> it = predicates.keySet().iterator();

			while (it.hasNext()) {
				String key = it.next();
				builder.append("@").append(key).append("=")
						.append(predicates.get(key));

				if (it.hasNext()) {
					builder.append(" and ");
				}
			}

			builder.append("]");
		}

		return builder;
	}

	@Override
	public String toString() {
		return toStringBuilder().toString();
	}

}
