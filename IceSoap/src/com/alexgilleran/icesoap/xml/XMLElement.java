package com.alexgilleran.icesoap.xml;

import java.io.IOException;

import org.xmlpull.v1.XmlSerializer;

public interface XMLElement {

	/**
	 * Appends the serialized representation of the element to the current
	 * serializer.
	 * 
	 * @param serializer
	 *            {@link XmlSerializer} to append to
	 * @throws IllegalArgumentException
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	void serialize(XmlSerializer serializer) throws IllegalArgumentException,
			IllegalStateException, IOException;

	/**
	 * Serializes the {@link XMLNode} to its String-based form... e.g.
	 * {@code<element><value>value</value></element>}
	 * 
	 * @return A string representation of the object.
	 */
	String toString();

}