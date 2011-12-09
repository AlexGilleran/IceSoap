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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((elements == null) ? 0 : elements.hashCode());
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

		for (int i = 0; i < this.elements.size(); i++) {
			if (!elements.get(i).matches(otherXPath.elements.get(i))) {
				return false;
			}
		}

		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		for (BasicXPathElement element : elements) {
			builder.append("/").append(element.toString());
		}

		return builder.toString();
	}
}
