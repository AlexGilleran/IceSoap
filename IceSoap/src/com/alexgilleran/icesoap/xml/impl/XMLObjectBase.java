/**
 * 
 */
package com.alexgilleran.icesoap.xml.impl;

import com.alexgilleran.icesoap.xml.XMLAttribute;
import com.alexgilleran.icesoap.xml.XMLElement;

/**
 * Abstract class for implementations of {@link XMLAttribute} and
 * {@link XMLElement}
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

	// Javadocs will be defined by classes further down the hierarchy.
	
	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}