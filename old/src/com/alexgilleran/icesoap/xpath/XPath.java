package com.alexgilleran.icesoap.xpath;

import java.util.ArrayList;
import java.util.List;

public class XPath {
	private List<XPathElement> elements = new ArrayList<XPathElement>();

	public XPath() {

	}

	public XPath(XPath... xpaths) {
		for (XPath xpath : xpaths) {
			for (XPathElement element : xpath.elements) {
				elements.add(element);
			}
		}
	}

	public void addElement(XPathElement node) {
		elements.add(node);
	}

	public void removeElement() {
		if (!elements.isEmpty()) {
			elements.remove(elements.size() - 1);
		}
	}

	public XPathElement getLastElement() {
		if (!elements.isEmpty()) {
			return elements.get(elements.size() - 1);
		} else {
			return null;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((elements == null) ? 0 : elements.hashCode());
		return result;
	}

	public XPath getWithoutPredicates() {
		XPath xpath = new XPath();

		for (XPathElement element : elements) {
			xpath.addElement(new XPathElement(element.getName()));
		}

		return xpath;
	}

	public boolean targetsAttribute() {
		return getLastElement().getAttribute() != null;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XPath other = (XPath) obj;
		if (elements == null) {
			if (other.elements != null)
				return false;
		} else if (!elements.equals(other.elements))
			return false;
		return true;
	}

	public boolean matches(XPath otherXPath) {
		if (this.elements.size() != otherXPath.elements.size()) {
			return false;
		}

		for (int i = elements.size() - 1; i >= 0; i--) {
			if (!elements.get(i).matches(otherXPath.elements.get(i))) {
				return false;
			}
		}

		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		for (XPathElement element : elements) {
			builder.append("/").append(element.toString());
		}

		return builder.toString();
	}
}
