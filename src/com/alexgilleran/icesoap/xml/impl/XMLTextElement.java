package com.alexgilleran.icesoap.xml.impl;

import java.io.IOException;

import org.xmlpull.v1.XmlSerializer;

/**
 * An XML text element - i.e. a node with nothing in it but a text value.
 * 
 * @author Alex Gilleran
 * 
 */
public class XMLTextElement extends XMLElementBase {
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
	public XMLTextElement(String namespace, String name, String value) {
		super(namespace, name);

		this.value = value;
	}

	/**
	 * Gets the text value for the text element.
	 * 
	 * @return The text value of the text element, or "" if no value was
	 *         specified.
	 */
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
}
