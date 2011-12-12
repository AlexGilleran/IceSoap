package com.alexgilleran.icesoap.xpath.elements;

public interface XPathElement {

	void addPredicate(String name, String value);

	boolean matches(XPathElement otherElement);

	String getPredicate(String predicateName);

	XPathElement getPreviousElement();

	boolean isFirstElement();

	StringBuilder toStringBuilder();
	
	boolean isAttribute();
}