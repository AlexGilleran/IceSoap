package com.alexgilleran.icesoap.xml.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.xmlpull.v1.XmlSerializer;

import com.alexgilleran.icesoap.parser.impl.PullParserFactory;
import com.alexgilleran.icesoap.xml.XMLAttribute;
import com.alexgilleran.icesoap.xml.XMLNode;

/**
 * Base implementation for XML Elements - both nodes and leaves.
 * 
 * @author Alex Gilleran
 * 
 */
public abstract class XMLNodeBase extends XMLObjectBase implements XMLNode {
	/** Name for xsi:type attribute. */
	private static final String TYPE_ATTRIBUTE_NAME = "type";
	/** Namespace prefixes declared in this element (map of prefixes to urls). */
	private Map<String, String> declaredPrefixes = new HashMap<String, String>();
	/** The attributes of the element. */
	private Set<XMLAttribute> attributes = new HashSet<XMLAttribute>();
	/**
	 * The xsi:type of the element - if this is set, it will be put into the
	 * attributes set
	 */
	private XMLAttribute type = null;

	/**
	 * Creates a new XML Element.
	 * 
	 * @param namespace
	 *            The namespace of the element (can be null).
	 * @param name
	 *            The name of the element.
	 */
	public XMLNodeBase(String namespace, String name) {
		super(namespace, name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<XMLAttribute> getAttributes() {
		return attributes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public XMLAttribute addAttribute(String namespace, String name, String value) {
		XMLAttributeImpl att = new XMLAttributeImpl(namespace, name, value);
		attributes.add(att);
		return att;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setType(String type) {
		// If there's been no type set, make a new attribute to represent it and
		// remember it in case it needs changing in the future
		if (this.type == null) {
			this.type = addAttribute(XMLNode.NS_URI_XSI, TYPE_ATTRIBUTE_NAME, type);
		} else {
			this.type.setValue(type);
		}
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
	public String toString() {
		try {
			XmlSerializer cereal = PullParserFactory.getInstance().buildSerializer();
			StringWriter writer = new StringWriter();

			cereal.setOutput(writer);

			serialize(cereal);

			cereal.flush();

			return writer.toString();
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalStateException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void serialize(XmlSerializer serializer) throws IllegalArgumentException, IllegalStateException, IOException {
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
	 *            XmlSerializer to append to
	 * 
	 * @throws IOException
	 *             If a general I/O error occurs.
	 */
	protected void serializePrefixes(XmlSerializer serializer) throws IOException {
		for (String key : declaredPrefixes.keySet()) {
			serializer.setPrefix(key, declaredPrefixes.get(key));
		}
	}

	/**
	 * Serializes the attributes of the class
	 * 
	 * @param serializer
	 *            XMLPull serializer to use for serialization
	 * @throws IOException
	 *             If a general I/O error occurs.
	 */
	protected void serializeAttributes(XmlSerializer serializer) throws IOException {
		for (XMLAttribute attribute : attributes) {
			serializer.attribute(attribute.getNamespace(), attribute.getName(), attribute.getValue());
		}
	}

	/**
	 * Serializes the content of the element, be it text or other elements.
	 * 
	 * @param serializer
	 *            XMLPull serializer to use for serialization.
	 * @throws IOException
	 *             If a general I/O error occurs.
	 */
	protected abstract void serializeContent(XmlSerializer serializer) throws IOException;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attributes == null) ? 0 : attributes.hashCode());
		result = prime * result + ((declaredPrefixes == null) ? 0 : declaredPrefixes.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		XMLNodeBase other = (XMLNodeBase) obj;
		if (attributes == null) {
			if (other.attributes != null)
				return false;
		} else if (!attributes.equals(other.attributes))
			return false;
		if (declaredPrefixes == null) {
			if (other.declaredPrefixes != null)
				return false;
		} else if (!declaredPrefixes.equals(other.declaredPrefixes))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}
