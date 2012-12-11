package com.alexgilleran.icesoap.xpath.elements.impl;

import com.alexgilleran.icesoap.xpath.elements.XPathElement;

/**
 * Represents a relative XPath element - i.e. one preceeded by no slash.
 * 
 * @author Alex Gilleran
 * 
 */
public class RelativeXPathElement extends SingleSlashXPathElement {
	/**
	 * The prefix to display for a relative element - as this is relative, this
	 * is blank.
	 */
	private static final String PREFIX = "";

	/**
	 * Instantiates a new {@link RelativeXPathElement}
	 * 
	 * @param name
	 *            The name of the new element
	 * @param previousElement
	 *            The previous element - note that this can be set to null.
	 */
	public RelativeXPathElement(String name, XPathElement previousElement) {
		super(name, previousElement);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getPrefix() {
		// If this is the first element, return "", otherwise act as if this
		// were a normal single-slash element
		if (isFirstElement()) {
			return PREFIX;
		} else {
			return super.getPrefix();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isRelative() {
		// If this is the first element it's relative, otherwise it acts as a
		// single-slash
		if (isFirstElement()) {
			return true;
		} else {
			return super.isRelative();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void setPreviousElement(XPathElement element) {
		super.setPreviousElement(element);
	}

	@Override
	public RelativeXPathElement clone() {
		XPathElement previousElement = getPreviousElement();

		if (previousElement != null) {
			previousElement = previousElement.clone();
		}

		RelativeXPathElement newElement = new RelativeXPathElement(getName(), previousElement);

		copyInto(newElement);

		return newElement;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Note that if this is called while there is no previous element then an
	 * Exception will be thrown, as <code>matches</code> cannot be resolved for
	 * a relative element.
	 */
	@Override
	public boolean matches(XPathElement otherElement) {
		if (isFirstElement()) {
			throw new RuntimeException("Attempt was made to match against relative xpath '" + this.toString()
					+ "' when the xpath was not attached to an absolute xpath.");
		}

		// If we get to here, there is a previous element so this should
		// function as its parent.
		return super.matches(otherElement);
	}
}
