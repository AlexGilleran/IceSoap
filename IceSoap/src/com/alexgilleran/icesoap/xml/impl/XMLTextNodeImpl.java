package com.alexgilleran.icesoap.xml.impl;

import java.io.IOException;

import org.xmlpull.v1.XmlSerializer;

import com.alexgilleran.icesoap.xml.XMLTextNode;

/**
 * Implementation of {@link XMLTextNode}.
 * 
 * @author Alex Gilleran
 * 
 */
public class XMLTextNodeImpl extends XMLNodeBase implements XMLTextNode {
	/** The blank value - returned if the value is null. */
	private static final String BLANK_VALUE = "";
	/** The text value of the text element. */
	private String value;

	/**
	 * Instantiates a new XML text element.
	 * 
	 * @param namespace
	 *            The namespace of the text element (can be null).
	 * @param name
	 *            The name of the text element.
	 * @param value
	 *            The value of the text element.
	 */
	public XMLTextNodeImpl(String namespace, String name, String value) {
		super(namespace, name);

		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getValue() {
		if (value == null) {
			return BLANK_VALUE;
		}

		return value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void serializeContent(XmlSerializer cereal) throws IllegalArgumentException, IllegalStateException,
			IOException {
		if (value == null) {
			cereal.attribute(NS_URI_XSI, XSI_NIL_NAME, XSI_NIL_TRUE);
		} else {
			cereal.text(getValue());
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		XMLTextNodeImpl other = (XMLTextNodeImpl) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
}
