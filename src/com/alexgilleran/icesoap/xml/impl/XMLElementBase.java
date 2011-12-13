package com.alexgilleran.icesoap.xml.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.xmlpull.v1.XmlSerializer;

import com.alexgilleran.icesoap.envelope.SOAPEnv;
import com.alexgilleran.icesoap.xml.XMLAttribute;
import com.alexgilleran.icesoap.xml.XMLElement;

/**
 * Base implementation for XML Elements - both nodes and leaves.
 * 
 * @author Alex Gilleran
 * 
 */
public abstract class XMLElementBase extends XMLObjectBase implements
		XMLElement {
	/** Namespace prefixes declared in this element */
	private Map<String, String> declaredPrefixes = new HashMap<String, String>();
	/** The attributes of the element */
	private Set<XMLAttributeImpl> attributes = new HashSet<XMLAttributeImpl>();

	/**
	 * Creates a new XML Element
	 * 
	 * @param namespace
	 *            The namespace of the element (can be null)
	 * @param name
	 *            The name of the element.
	 */
	public XMLElementBase(String namespace, String name) {
		super(namespace, name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<XMLAttributeImpl> getAttributes() {
		return attributes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addAttribute(String namespace, String name, String value) {
		XMLAttributeImpl att = new XMLAttributeImpl(namespace, name, value);
		attributes.add(att);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setType(String type) {
		this.addAttribute(SOAPEnv.NS_URI_XSI, "type", type);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void declarePrefix(String prefix, String namespace) {
		declaredPrefixes.put(prefix, namespace);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(XmlSerializer serializer)
			throws IllegalArgumentException, IllegalStateException, IOException {
		serializePrefixes(serializer);

		serializer.startTag(namespace, name);

		serializeAttributes(serializer);
		serializeContent(serializer);

		serializer.endTag(namespace, name);
	}

	/**
	 * Serializes the prefixes before the tag is started.
	 * 
	 * @param serializer
	 * @throws IllegalArgumentException
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	protected void serializePrefixes(XmlSerializer serializer)
			throws IllegalArgumentException, IllegalStateException, IOException {
		for (String key : declaredPrefixes.keySet()) {
			serializer.setPrefix(key, declaredPrefixes.get(key));
		}
	}

	/**
	 * Serializes the attributes of the class
	 * 
	 * @param serializer
	 *            XMLPull serializer to use for serialization
	 * @throws IllegalArgumentException
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	protected void serializeAttributes(XmlSerializer serializer)
			throws IllegalArgumentException, IllegalStateException, IOException {
		for (XMLAttribute attribute : attributes) {
			serializer.attribute(attribute.getNamespace(), attribute.getName(),
					attribute.getValue());
		}
	}

	/**
	 * Serializes the content of the element, be it text or other elements.
	 * 
	 * @param serializer
	 *            XMLPull serializer to use for serialization
	 * @throws IllegalArgumentException
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	protected abstract void serializeContent(XmlSerializer serializer)
			throws IllegalArgumentException, IllegalStateException, IOException;
}
