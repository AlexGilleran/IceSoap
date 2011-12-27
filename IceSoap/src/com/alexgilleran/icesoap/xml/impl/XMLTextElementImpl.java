package com.alexgilleran.icesoap.xml.impl;

import java.io.IOException;

import org.xmlpull.v1.XmlSerializer;

import com.alexgilleran.icesoap.xml.XMLTextElement;

/**
 * Implementation of {@link XMLTextElement}
 * 
 * @author Alex Gilleran
 * 
 */
public class XMLTextElementImpl extends XMLElementBase implements
		XMLTextElement {
	/** The blank value - returned if the value is null */
	private static final String BLANK_VALUE = "";
	/** The text value of the text element. */
	private String value;

	/**
	 * Instantiates a new XML text element
	 * 
	 * @param namespace
	 *            The namespace of the text element (can be null)
	 * @param name
	 *            The name of the text element.
	 * @param value
	 *            The value of the text element.
	 */
	public XMLTextElementImpl(String namespace, String name, String value) {
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
	protected void serializeContent(XmlSerializer cereal)
			throws IllegalArgumentException, IllegalStateException, IOException {
		cereal.text(value);
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
		XMLTextElementImpl other = (XMLTextElementImpl) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
}
