package com.alexgilleran.icesoap.xml.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.xmlpull.v1.XmlSerializer;

import com.alexgilleran.icesoap.xml.XMLElement;
import com.alexgilleran.icesoap.xml.XMLNode;
import com.alexgilleran.icesoap.xml.XMLTextElement;

/**
 * Implementation of {@link XMLNode}
 * 
 * @author Alex Gilleran
 * 
 */
public class XMLNodeImpl extends XMLElementBase implements XMLNode {
	/** List of subelements of the node */
	private List<XMLElement> subElements = new ArrayList<XMLElement>();

	/**
	 * Instantiates a new {@link XMLNodeImpl} with the specified namespace and
	 * name.
	 * 
	 * @param namespace
	 *            The namespace for the new node.
	 * @param name
	 *            The name for the new node.
	 */
	public XMLNodeImpl(String namespace, String name) {
		super(namespace, name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<XMLElement> getSubElements() {
		return Collections.unmodifiableList(subElements);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public XMLNode addNode(String namespace, String name) {
		XMLNodeImpl newNode = new XMLNodeImpl(namespace, name);

		subElements.add(newNode);

		return newNode;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public XMLElement addElement(XMLElement element) {
		subElements.add(element);

		return element;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public XMLTextElement addTextElement(String namespace, String name,
			String value) {
		XMLTextElementImpl newTextElement = new XMLTextElementImpl(namespace,
				name, value);

		subElements.add(newTextElement);

		return newTextElement;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void serializeContent(XmlSerializer cereal)
			throws IllegalArgumentException, IllegalStateException, IOException {
		for (XMLElement element : subElements) {
			element.serialize(cereal);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((subElements == null) ? 0 : subElements.hashCode());
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
		XMLNodeImpl other = (XMLNodeImpl) obj;
		if (subElements == null) {
			if (other.subElements != null)
				return false;
		} else if (!subElements.equals(other.subElements))
			return false;
		return true;
	}
}
