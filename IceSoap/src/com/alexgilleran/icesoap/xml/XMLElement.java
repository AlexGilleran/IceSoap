/**
 * 
 */
package com.alexgilleran.icesoap.xml;

import java.io.IOException;
import java.util.Collection;

import org.xmlpull.v1.XmlSerializer;

import com.alexgilleran.icesoap.xml.impl.XMLAttributeImpl;

/**
 * Interface that represents a single XML Element within an XML document.
 * 
 * @author Alex Gilleran
 * 
 */
public interface XMLElement {
	/**
	 * Get all the attributes of this element.
	 * 
	 * @return The attributes as a collection of {@link XMLAttributeImpl}
	 *         objects.
	 */
	Collection<XMLAttribute> getAttributes();

	/**
	 * Adds an attribute to the element.
	 * 
	 * @param namespace
	 *            The namespace of the element as a URI - can be null if no
	 *            namespace is to be set.
	 * @param name
	 *            The name of the attribute.
	 * @param value
	 *            The value of the attribute.
	 * @return
	 */
	XMLAttribute addAttribute(String namespace, String name, String value);

	/**
	 * Sets the <code>xsi:type</code> attribute for the element.
	 * 
	 * @param type
	 *            The type, as a string.
	 */
	void setType(String type);

	/**
	 * Declare a prefix for a namespace URI.
	 * 
	 * @param prefix
	 *            The prefix name (e.g. "xsi").
	 * @param namespace
	 *            The namespace URI, as a String.
	 */
	void declarePrefix(String prefix, String namespace);

	/**
	 * Get the namespace URI for this element.
	 * 
	 * @return The namespace URI, as a String.
	 */
	String getNamespace();

	/**
	 * Sets the namespace of this element.
	 * 
	 * @param namespace
	 *            The namespace URI as a String.
	 */
	void setNamespace(String namespace);

	/**
	 * Serializes the contents of this element and any elements it contains.
	 * 
	 * @param serializer
	 *            An XmlPull serializer
	 * @throws IllegalArgumentException
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	void serialize(XmlSerializer serializer) throws IllegalArgumentException,
			IllegalStateException, IOException;
}