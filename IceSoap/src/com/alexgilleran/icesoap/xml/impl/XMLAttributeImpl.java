package com.alexgilleran.icesoap.xml.impl;

import com.alexgilleran.icesoap.xml.XMLAttribute;

/**
 * Basic implementation of {@link XMLAttribute}.
 * 
 * @author Alex Gilleran
 * 
 */
public class XMLAttributeImpl extends XMLObjectBase implements XMLAttribute {
	/** Value of the attribute. */
	private String value;

	/**
	 * @param namespace
	 *            The namespace of the attribute (can be null).
	 * @param name
	 *            The name of the attribute.
	 * @param value
	 *            The value of the attribute.
	 */
	public XMLAttributeImpl(String namespace, String name, String value) {
		super(namespace, name);

		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getValue() {
		return value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((namespace == null) ? 0 : namespace.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		XMLAttributeImpl other = (XMLAttributeImpl) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (namespace == null) {
			if (other.namespace != null)
				return false;
		} else if (!namespace.equals(other.namespace))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
}
