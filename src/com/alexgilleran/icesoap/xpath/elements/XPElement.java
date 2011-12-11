package com.alexgilleran.icesoap.xpath.elements;

public interface XPElement {

	void addPredicate(String name, String value);

	boolean matches(XPElement otherElement);

	String getPredicate(String predicateName);

	XPElement getPreviousElement();

	boolean isFirstElement();
}