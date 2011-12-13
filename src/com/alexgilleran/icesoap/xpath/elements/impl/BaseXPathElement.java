package com.alexgilleran.icesoap.xpath.elements.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.alexgilleran.icesoap.xpath.elements.XPathElement;

/**
 * Represents base logic that applies to all XPathElement implementations unless
 * overridden.
 * 
 * Note that when extending this class, if you add any extra fields it's
 * imperative that you override hashCode and equals, as these are used by the
 * XPathRepository.
 * 
 * @author Alex Gilleran
 * 
 */
public abstract class BaseXPathElement implements XPathElement {
	/** The name of the element */
	private String name;
	/** The previous element */
	private XPathElement previousElement;
	/**
	 * A map representing all the predicates for this element, mapping from name
	 * to value
	 */
	private Map<String, String> predicates = new HashMap<String, String>();

	/**
	 * Instantiates a new BaseXPathElement
	 * 
	 * @param name
	 *            The name of the new element
	 * @param previousElement
	 *            The previous element - note that this can be set to null.
	 */
	public BaseXPathElement(String name, XPathElement previousElement) {
		this.name = name;
		this.previousElement = previousElement;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public XPathElement getPreviousElement() {
		return previousElement;
	}

	/**
	 * <p>
	 * Sets the previous element to this one in the XPath expression. Note that
	 * an xpath element will retain the behaviour determined by how many slashes
	 * prefix it.
	 * </p>
	 * <p>
	 * E.g. If I have an XPath <code>//allnode/xpath</code> and set
	 * <code>allnode</code>'s previous element as <code>/element</code>, the
	 * resulting entire XPath will be <code>/element//allnode/xpath</code>.
	 * </p>
	 * <p>
	 * Note that for relative XPaths, setting a previous element changes it to a
	 * standard single-slash XPath element. E.g. if I have a relative xpath
	 * <code>allnode/xpath</code> and set <code>allnode</code>'s previous
	 * element to <code>/element</code> as above, the resulting entire XPath
	 * will become <code>/element/allnode/xpath</code>.
	 * 
	 * @param element
	 *            The element to set as the previous element to this one.
	 */
	protected void setPreviousElement(XPathElement element) {
		previousElement = element;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAttribute() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isFirstElement() {
		return previousElement == null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addPredicate(String name, String value) {
		predicates.put(name, value);
	}

	/**
	 * Determines whether the passed element has the same name and the correct
	 * value for all specified predicates of this element.
	 */
	@Override
	public boolean matches(XPathElement otherElement) {
		if (!getName().equals(otherElement.getName())) {
			return false;
		}

		// Explanation: Basically this goes through and finds any reason that
		// two xpaths *won't* match, in order of most likely reason to least
		// likely - if none of these reasons are present, returns true

		for (String predicateKey : predicates.keySet()) {
			if (otherElement.getPredicate(predicateKey) == null) {
				return false;
			}

			if (!this.predicates.get(predicateKey).equals(
					otherElement.getPredicate(predicateKey))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Gets the prefix of the element... e.g. "/", "//" etc.
	 * 
	 * @return The prefix of the element as a string.
	 */
	protected abstract String getPrefix();

	/**
	 * {@inheritDoc}
	 */
	public StringBuilder toStringBuilder() {
		StringBuilder builder;

		// If this isn't the first element, the string representation of this
		// will build on that of previous elements in the whole xpath
		if (isFirstElement()) {
			builder = new StringBuilder();
		} else {
			builder = getPreviousElement().toStringBuilder();
		}

		builder.append(getPrefix()).append(getName());

		if (!predicates.isEmpty()) {
			builder.append("[");

			Iterator<String> it = predicates.keySet().iterator();

			while (it.hasNext()) {
				String key = it.next();
				builder.append("@").append(key).append("=").append("\"")
						.append(predicates.get(key)).append("\"");

				if (it.hasNext()) {
					builder.append(" and ");
				}
			}

			builder.append("]");
		}

		return builder;
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
	public String getPredicate(String predicateName) {
		return predicates.get(predicateName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isRelative() {
		// This is overridden by RelativeXPathElement
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((predicates == null) ? 0 : predicates.hashCode());
		result = prime * result
				+ ((previousElement == null) ? 0 : previousElement.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseXPathElement other = (BaseXPathElement) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (predicates == null) {
			if (other.predicates != null)
				return false;
		} else if (!predicates.equals(other.predicates))
			return false;
		if (previousElement == null) {
			if (other.previousElement != null)
				return false;
		} else if (!previousElement.equals(other.previousElement))
			return false;
		return true;
	}

}