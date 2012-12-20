package com.alexgilleran.icesoap.xpath.elements.impl;

import com.alexgilleran.icesoap.xpath.elements.XPathElement;

/**
 * Represents XPath elements that start with a double-slash ("//").
 * 
 * @author Alex Gilleran
 * 
 */
public class DoubleSlashXPathElement extends BaseXPathElement implements Cloneable {
	/** The prefix of this element when represented as a String. */
	private static final String PREFIX = "//";

	/**
	 * Instantiates a new {@link DoubleSlashXPathElement}
	 * 
	 * @param name
	 *            The name of the new element.
	 * @param previousElement
	 *            The previous element - note that this can be set to null.
	 */
	public DoubleSlashXPathElement(String name, XPathElement previousElement) {
		super(name, previousElement);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean matches(XPathElement otherElement) {
		// Make sure these match at a basic level
		if (!super.matches(otherElement)) {
			return false;
		}

		// If a // element is the first element, return true as anything before
		// it is allowed.
		if (!this.isFirstElement()) {
			// Loop through all the previous elements that the other element
			// has, to see if any of them match this node's previous element.

			XPathElement thisPreviousElement = otherElement.getPreviousElement();

			while (thisPreviousElement != null) {
				if (this.getPreviousElement().matches(thisPreviousElement)) {
					// the .matches function will have checked all the previous
					// elements, so return true.
					return true;
				}

				// This element didn't match - check the previous one.
				thisPreviousElement = thisPreviousElement.getPreviousElement();
			}

			// No matching element could be found - return false.
			return false;
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

	@Override
	public DoubleSlashXPathElement clone() {
		XPathElement previousElement = getPreviousElement();

		if (previousElement != null) {
			previousElement = previousElement.clone();
		}

		DoubleSlashXPathElement newElement = new DoubleSlashXPathElement(getName(), previousElement);

		copyInto(newElement);

		return newElement;
	}
}