package com.alexgilleran.icesoap.xml.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

import com.alexgilleran.icesoap.xml.XMLAttribute;
import com.alexgilleran.icesoap.xml.XMLElement;
import com.alexgilleran.icesoap.xml.XMLSerializerFactory;

/**
 * Base implementation for XML Elements - both nodes and leaves.
 * 
 * @author Alex Gilleran
 * 
 */
public abstract class XMLElementBase extends XMLObjectBase implements
		XMLElement {
	/** Name for xsi:type attribute */
	private static final String TYPE_ATTRIBUTE_NAME = "type";
	/** Namespace prefixes declared in this element (map of prefixes to urls) */
	private Map<String, String> declaredPrefixes = new HashMap<String, String>();
	/** The attributes of the element */
	private Set<XMLAttribute> attributes = new HashSet<XMLAttribute>();
	/** Used to obtain an implementation of {@link XmlSerializer} */
	private XMLSerializerFactory serializerFactory;
	/**
	 * The xsi:type of the element - if this is set, it will be put into the
	 * attributes set
	 */
	private XMLAttribute type = null;

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
			this.type = addAttribute(XMLElement.NS_URI_XSI, TYPE_ATTRIBUTE_NAME,
					type);
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

	public String toString() {
		try {
			XmlSerializer cereal = getSerializerFactory().buildXmlSerializer();
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

	/**
	 * Sets the {@link XMLSerializerFactory} to be used for obtaining an
	 * implementation of {@link XmlSerializer}
	 * 
	 * @param serializerFactory
	 *            The factory to use.
	 */
	public void setSerializerFactory(XMLSerializerFactory serializerFactory) {
		this.serializerFactory = serializerFactory;
	}

	/**
	 * Gets a serializer factory - if none has been set by a client class,
	 * returns the standard {@link AndroidSerializerFactory} implementation.
	 * 
	 * @return A serializer factory.
	 */
	private XMLSerializerFactory getSerializerFactory() {
		if (serializerFactory == null) {
			return ANDROID_SERIALIZER_FACTORY;
		} else {
			return serializerFactory;
		}
	}

	/**
	 * Standard implementation of {@link XMLSerializerFactory} that uses the
	 * standard Android method of getting a new instance. This will return an
	 * exception if used outside Android normally, or a null object (every
	 * method returns null) if used in Robolectric 1.0 - override with
	 * {@link XMLElementBase#setSerializerFactory(XMLSerializerFactory)} in
	 * these circumstances.
	 */
	private final static XMLSerializerFactory ANDROID_SERIALIZER_FACTORY = new XMLSerializerFactory() {
		/** {@inheritDoc} */
		@Override
		public XmlSerializer buildXmlSerializer() {
			return Xml.newSerializer();
		}
	};
}
