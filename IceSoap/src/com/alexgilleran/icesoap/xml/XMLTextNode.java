/**
 * 
 */
package com.alexgilleran.icesoap.xml;

/**
 * 
 * An XML text element - i.e. a node with nothing in it but a text value.
 * 
 * @author Alex Gilleran
 * 
 */
public interface XMLTextNode extends XMLNode {

	/**
	 * Gets the text value for the text element.
	 * 
	 * @return The text value of the text element, or "" if no value was
	 *         specified.
	 */
	public abstract String getValue();

}