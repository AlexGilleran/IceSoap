/**
 * 
 */
package com.alexgilleran.icesoap.xml;

import java.util.List;

/**
 * Represents an XMLNode - an XML element with zero or more other elements
 * beneath it in the XML hierarchy. Note that this does <i>not</i> represent
 * nodes with text under them, that's the preserve of {@link XMLTextElement}.
 * 
 * @author Alex Gilleran
 */
public interface XMLNode extends XMLElement {
	/**
	 * Gets all the subelements of this node, in the order that they were added.
	 * 
	 * @return The subelements of the node in order.
	 */
	List<XMLElement> getSubElements();

	/**
	 * Creates new node object with the specified namespace and name, and adds
	 * it to this node.
	 * 
	 * @param namespace
	 *            The namespace URI to set for the new node
	 * @param name
	 *            The name to set for the new node
	 * @return The newly created node.
	 */
	XMLNode addNode(String namespace, String name);

	/**
	 * Adds the passed {@link XMLElement} to the end of the node.
	 * 
	 * @param element
	 *            The {@link XMLElement} to add to the node.
	 * @return The element that's just been added.
	 */
	XMLElement addElement(XMLElement element);

	/**
	 * Creates a new text element with the specified namespace, name and value
	 * and adds it to the end of the node.
	 * 
	 * @param namespace
	 *            The namespace URI to set for the new text element
	 * @param name
	 *            The name to set for the new text element
	 * @param value
	 *            The value of the new text element
	 * @return The newly created text element.
	 */
	XMLTextElement addTextElement(String namespace, String name, String value);

}