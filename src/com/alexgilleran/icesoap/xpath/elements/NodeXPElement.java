package com.alexgilleran.icesoap.xpath.elements;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class NodeXPElement extends BaseXPElement {
	private Map<String, String> predicates = new HashMap<String, String>();

	public NodeXPElement(String name, BaseXPElement previousElement) {
		super(name, previousElement);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.alexgilleran.icesoap.xpath.elements.XPElement#addPredicate(java.lang
	 * .String, java.lang.String)
	 */
	@Override
	public void addPredicate(String name, String value) {
		predicates.put(name, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.alexgilleran.icesoap.xpath.elements.XPElement#matches(com.alexgilleran
	 * .icesoap.xpath.elements.BaseXPElement)
	 */
	@Override
	public boolean matches(XPElement otherElement) {
		if (!super.matches(otherElement)) {
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

	protected abstract String getPrefix();

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
				builder.append("@").append(key).append("=")
						.append(predicates.get(key));

				if (it.hasNext()) {
					builder.append(" and ");
				}
			}

			builder.append("]");
		}

		return builder;
	}

	@Override
	public String toString() {
		return toStringBuilder().toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.alexgilleran.icesoap.xpath.elements.XPElement#getPredicate(java.lang
	 * .String)
	 */
	@Override
	public String getPredicate(String predicateName) {
		return predicates.get(predicateName);
	}

}