package com.alexgilleran.icesoap.xpath.elements.impl;

import com.alexgilleran.icesoap.xpath.elements.XPathElement;

/**
 * Wrapper for XPathElement to represents XPath elements that describe an
 * attribute, e.g. <code>/@attributename</code>.
 * 
 * Note that unlike most implementations of XPathElement, this simply wraps
 * around an existing XPathElement to enhance it with attribute-specific logic.
 * 
 * @author Alex Gilleran
 * 
 */
public class AttributeXPathElement implements XPathElement {
	/** Prefix applied to attributes when representing as a {@link String} */
	private final static String XPATH_ATTRIBUTE_PREFIX = "@";
	/** The wrapped XPath element. */
	private XPathElement wrappedElement;

	/**
	 * Instantiates a new {@link AttributeXPathElement}
	 * 
	 * @param name
	 *            The name of the new element
	 * @param previousElement
	 *            The previous element - note that this can be set to null.
	 */
	public AttributeXPathElement(XPathElement wrappedElement) {
		this.wrappedElement = wrappedElement;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean matches(XPathElement otherElement) {
		// Make sure the other element is also an attribute.
		if (!otherElement.isAttribute()) {
			return false;
		}

		// If other element is an attribute it must be castable to
		// AttributeXPathElement
		AttributeXPathElement castOtherElement = (AttributeXPathElement) otherElement;
		if (!wrappedElement.matches(castOtherElement.wrappedElement)) {
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
		return wrappedElement.getPrefix() + XPATH_ATTRIBUTE_PREFIX;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAttribute() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return wrappedElement.getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public XPathElement getPreviousElement() {
		return wrappedElement.getPreviousElement();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isFirstElement() {
		return wrappedElement.isFirstElement();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StringBuilder toStringBuilder() {
		return wrappedElement.getPreviousElement().toStringBuilder().append(this.getPrefix()).append(this.getName());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return toStringBuilder().toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isRelative() {
		return wrappedElement.isRelative();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPreviousElement(XPathElement previousElement) {
		wrappedElement.setPreviousElement(previousElement);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((wrappedElement == null) ? 0 : wrappedElement.hashCode());
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
		AttributeXPathElement other = (AttributeXPathElement) obj;
		if (wrappedElement == null) {
			if (other.wrappedElement != null)
				return false;
		} else if (!wrappedElement.equals(other.wrappedElement))
			return false;
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public XPathElement getFirstElement() {
		return wrappedElement.getFirstElement();
	}

	@Override
	public AttributeXPathElement clone() {
		AttributeXPathElement newElement = new AttributeXPathElement(wrappedElement.clone());

		return newElement;
	}
}
