/**
 * 
 */
package com.alexgilleran.icesoap.xml;

import java.util.List;

/**
 * Represents an XMLNode - an XML element with zero or more other elements
 * beneath it in the XML hierarchy. Note that this does <i>not</i> represent
 * nodes with text under them, that's the preserve of {@link XMLTextNode}.
 * 
 * @author Alex Gilleran
 */
public interface XMLParentNode extends XMLNode {
	/**
	 * Gets all the child nodes of this node, in the order that they were added.
	 * 
	 * @return The child nodes of the node in order.
	 */
	List<XMLElement> getChildNodes();

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
	XMLParentNode addNode(String namespace, String name);

	/**
	 * Adds the passed {@link XMLElement} to the end of the node.
	 * 
	 * @param node
	 *            The {@link XMLElement} to add to the node.
	 * @return The element that's just been added.
	 */
	XMLElement addElement(XMLElement node);

	/**
	 * Creates a new text node with the specified namespace, name and value and
	 * adds it to the end of the node.
	 * 
	 * @param namespace
	 *            The namespace URI to set for the new text element
	 * @param name
	 *            The name to set for the new text element
	 * @param value
	 *            The value of the new text element
	 * @return The newly created text element.
	 */
	XMLTextNode addTextNode(String namespace, String name, String value);

}