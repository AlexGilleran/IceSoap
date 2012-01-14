/**
 * 
 */
package com.alexgilleran.icesoap.xml;

/**
 * Represents an attribute on an XML node.
 * 
 * @author Alex Gilleran
 * 
 */
public interface XMLAttribute {

	/**
	 * Gets the namespace URI of the attribute as a string.
	 * 
	 * @return
	 */
	String getNamespace();

	/**
	 * Sets the namespace URI of an attribute.
	 * 
	 * @param namespace
	 *            Namespace URI as a string.
	 */
	void setNamespace(String namespace);

	/**
	 * Gets the name of the attribute.
	 * 
	 * @return The attribute name as a String.
	 */
	String getName();

	/**
	 * Sets the name of the attribute.
	 * 
	 * @param name
	 *            The new name for the attribute.
	 */
	void setName(String name);

	/**
	 * Gets the value of the attribute.
	 * 
	 * @return The value of the attribute in String format.
	 */
	String getValue();

	/**
	 * Set the value of the attribute
	 * 
	 * @param value
	 *            The new value as a String.
	 */
	void setValue(String value);

}