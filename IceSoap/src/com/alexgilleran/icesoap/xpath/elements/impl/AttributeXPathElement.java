package com.alexgilleran.icesoap.xpath.elements.impl;

import com.alexgilleran.icesoap.xpath.elements.XPathElement;

/**
 * Represents XPath elements that describe an attribute, e.g.
 * <code>/@attributename</code>.
 * 
 * @author Alex Gilleran
 * 
 */
public class AttributeXPathElement extends SingleSlashXPathElement {
	/**
	 * Instantiates a new {@link AttributeXPathElement}
	 * 
	 * @param name
	 *            The name of the new element
	 * @param previousElement
	 *            The previous element - note that this can be set to null.
	 */
	public AttributeXPathElement(String name, XPathElement previousElement) {
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

		// Make sure the other element is also an attrbute.
		if (!otherElement.isAttribute()) {
			return false;
		}

		return true;
	}

	/**
	 * This element is overridden for attribute elements, as they cannot have
	 * predicates - this does nothing.
	 */
	@Override
	public String getPredicate(String predicateName) {
		// Do nothing

		return null;
	}

	/**
	 * This element is overridden for attribute elements, as they cannot have
	 * predicates - this does nothing.
	 */
	@Override
	public void addPredicate(String name, String value) {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getPrefix() {
		return super.getPrefix() + "@";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAttribute() {
		return true;
	}
}
