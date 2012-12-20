package com.alexgilleran.icesoap.xml.impl;

import java.io.IOException;

import org.xmlpull.v1.XmlSerializer;

import com.alexgilleran.icesoap.xml.XMLElement;

/**
 * Represent the text part of an XML node.
 * 
 * @author Alex Gilleran
 * 
 */
public class XMLTextElement implements XMLElement {
	/** The text content of the element. */
	private String content;

	/**
	 * Creates a new instance
	 * 
	 * @param content
	 *            The string content of the element.
	 */
	public XMLTextElement(String content) {
		this.content = content;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(XmlSerializer serializer) throws IOException {
		serializer.text(content);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return content;
	}
}
