package com.alexgilleran.icesoap.xpath.elements.impl;

import com.alexgilleran.icesoap.xpath.elements.XPathElement;

/**
 * Represents an XPath element that starts with a single slash ("/").
 * 
 * @author Alex Gilleran
 * 
 */
public class SingleSlashXPathElement extends BaseXPathElement {

	/** The prefix of a single slash element is / */
	private static final String PREFIX = "/";

	/**
	 * Instantiates a new {@link SingleSlashXPathElement}
	 * 
	 * @param name
	 *            The name of the new element
	 * @param previousElement
	 *            The previous element - note that this can be set to null.
	 */
	public SingleSlashXPathElement(String name, XPathElement previousElement) {
		super(name, previousElement);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean matches(XPathElement otherElement) {
		if (!super.matches(otherElement)) {
			return false;
		}

		if (this.isFirstElement() != otherElement.isFirstElement()) {
			return false;
		}

		if (!this.isFirstElement()) {
			// Previous element is not null and this isn't a '//' element -
			// see if the previous elements of both xpaths match
			return getPreviousElement().matches(
					otherElement.getPreviousElement());
		}

		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getPrefix() {
		return PREFIX;
	}
}
