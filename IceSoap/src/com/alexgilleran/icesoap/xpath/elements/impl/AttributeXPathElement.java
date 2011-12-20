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
		if (!wrappedElement.matches(otherElement)) {
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
		return wrappedElement.getPrefix() + "@";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAttribute() {
		return true;
	}

	public String getName() {
		return wrappedElement.getName();
	}

	public XPathElement getPreviousElement() {
		return wrappedElement.getPreviousElement();
	}

	public boolean isFirstElement() {
		return wrappedElement.isFirstElement();
	}

	public StringBuilder toStringBuilder() {
		return wrappedElement.getPreviousElement().toStringBuilder()
				.append(this.getPrefix()).append(this.getName());
	}

	public String toString() {
		return toStringBuilder().toString();
	}

	public boolean isRelative() {
		return wrappedElement.isRelative();
	}

	@Override
	public void setPreviousElement(XPathElement previousElement) {
		wrappedElement.setPreviousElement(previousElement);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((wrappedElement == null) ? 0 : wrappedElement.hashCode());
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
}
