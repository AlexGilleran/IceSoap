package com.alexgilleran.icesoap.xpath.elements;

public interface XPathElement {
	String getName();

	void addPredicate(String name, String value);

	boolean matches(XPathElement otherElement);

	String getPredicate(String predicateName);

	XPathElement getPreviousElement();

	boolean isFirstElement();

	StringBuilder toStringBuilder();

	boolean isAttribute();
	
	int hashCode();
	
	boolean equals(Object obj);
}