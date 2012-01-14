/**
 * 
 */
package com.alexgilleran.icesoap.xml.impl;

import com.alexgilleran.icesoap.xml.XMLAttribute;
import com.alexgilleran.icesoap.xml.XMLNode;

/**
 * Abstract class for implementations of {@link XMLAttribute} and
 * {@link XMLNode}
 * 
 * @author Alex Gilleran
 * 
 */
public abstract class XMLObjectBase {

	/** The namespace of the element */
	protected String namespace;
	/** The name of the attribute */
	protected String name;

	/**
	 * Instantiates a new XMLObject
	 * 
	 * @param namespace
	 *            The namespace of the object (can be null).
	 * @param name
	 *            The name of the object.
	 */
	public XMLObjectBase(String namespace, String name) {
		this.namespace = namespace;
		this.name = name;
	}

	/**
	 * {inheritDoc}
	 */
	public String getNamespace() {
		return namespace;
	}

	/**
	 * {inheritDoc}
	 */
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	/**
	 * {inheritDoc}
	 */
	public String getName() {
		return name;
	}

	/**
	 * {inheritDoc}
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((namespace == null) ? 0 : namespace.hashCode());
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
		XMLObjectBase other = (XMLObjectBase) obj;
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
		return true;
	}
}