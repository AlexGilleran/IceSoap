package com.alexgilleran.icesoap.xpath.elements;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class BaseXPathElement implements XPathElement {
	private String name;
	private XPathElement previousElement;
	private Map<String, String> predicates = new HashMap<String, String>();

	public BaseXPathElement(String name, XPathElement previousElement) {
		this.name = name;
		this.previousElement = previousElement;
	}

	public XPathElement getPreviousElement() {
		return previousElement;
	}

	public void setPreviousElement(XPathElement element) {
		previousElement = element;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean isAttribute() {
		return false;
	}

	public boolean isFirstElement() {
		return previousElement == null;
	}

	@Override
	public void addPredicate(String name, String value) {
		predicates.put(name, value);
	}

	@Override
	public boolean matches(XPathElement otherElement) {
		if (!getName().equals(otherElement.getName())) {
			return false;
		}

		// Explanation: Basically this goes through and finds any reason that
		// two xpaths *won't* match, in order of most likely reason to least
		// likely - if none of these reasons are present, returns true

		for (String predicateKey : predicates.keySet()) {
			if (otherElement.getPredicate(predicateKey) == null) {
				return false;
			}

			if (!this.predicates.get(predicateKey).equals(
					otherElement.getPredicate(predicateKey))) {
				return false;
			}
		}

		return true;
	}

	protected abstract String getPrefix();

	public StringBuilder toStringBuilder() {
		StringBuilder builder;

		// If this isn't the first element, the string representation of this
		// will build on that of previous elements in the whole xpath
		if (isFirstElement()) {
			builder = new StringBuilder();
		} else {
			builder = getPreviousElement().toStringBuilder();
		}

		builder.append(getPrefix()).append(getName());

		if (!predicates.isEmpty()) {
			builder.append("[");

			Iterator<String> it = predicates.keySet().iterator();

			while (it.hasNext()) {
				String key = it.next();
				builder.append("@").append(key).append("=").append("\"")
						.append(predicates.get(key)).append("\"");

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

	@Override
	public String getPredicate(String predicateName) {
		return predicates.get(predicateName);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((predicates == null) ? 0 : predicates.hashCode());
		result = prime * result
				+ ((previousElement == null) ? 0 : previousElement.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseXPathElement other = (BaseXPathElement) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (predicates == null) {
			if (other.predicates != null)
				return false;
		} else if (!predicates.equals(other.predicates))
			return false;
		if (previousElement == null) {
			if (other.previousElement != null)
				return false;
		} else if (!previousElement.equals(other.previousElement))
			return false;
		return true;
	}

}