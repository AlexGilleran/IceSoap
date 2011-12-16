/**
 * 
 */
package com.alexgilleran.icesoap.xml;

import java.util.Collection;

import com.alexgilleran.icesoap.xml.impl.XMLAttributeImpl;

/**
 * Interface that represents a single XML Element within an XML document.
 * 
 * @author Alex Gilleran
 * 
 */
public interface XMLElement {
	/** Prefix for the XMLSchema namespace */
	public final static String NS_PREFIX_XSD = "xsd";
	/** URL of the XMLSchema namespace */
	public final static String NS_URI_XSD = "http://www.w3.org/2001/XMLSchema";
	/** Prefix for the XmlSchema-instance namespace */
	public final static String NS_PREFIX_XSI = "xsi";
	/** URL of the XmlSchema-instance namespace */
	public final static String NS_URI_XSI = "http://www.w3.org/2001/XMLSchema-instance";

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
	 * Note that this basically adds a new attribute called "type" in the
	 * "http://www.w3.org/2001/XMLSchema-instance" namespace - it doesn't
	 * automatically declare this namespace with the "xsi" prefix. If the xsi
	 * prefix is declared with {@link XMLElement#declarePrefix(String, String)}
	 * method on this element or any higher elements, it this will come out as
	 * xsi:type.
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
	 * Serializes the {@link XMLElement} to its String-based form... e.g.
	 * {@code<element><value>value</value></element>}
	 * 
	 * @return A string representation of the object.
	 */
	String toString();
}