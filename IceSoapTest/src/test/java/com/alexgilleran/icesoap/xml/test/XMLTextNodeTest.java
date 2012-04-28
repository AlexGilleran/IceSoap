/**
 * 
 */
package com.alexgilleran.icesoap.xml.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.alexgilleran.icesoap.xml.XMLNode;
import com.alexgilleran.icesoap.xml.XMLTextNode;
import com.alexgilleran.icesoap.xml.impl.XMLTextNodeImpl;

/**
 * Tests that the {@link XMLTextNodeImpl} class, which represents basic XML Text
 * nodes in the format {@code <element>value</element>}, works as desired.
 * 
 * @author Alex Gilleran
 * 
 */
public class XMLTextNodeTest extends XMLElementTest<XMLTextNodeImpl> {
	/** Basic namespace to pass up the class hierarchy */
	private final static String DEFAULT_NAMESPACE = "http://www.example.com";
	/** Basic name to pass up the class hierarchy */
	private final static String DEFAULT_NAME = "textelement";
	/** Basic text value */
	private static final String DEFAULT_VALUE = "value";

	public XMLTextNodeTest() {
		super(DEFAULT_NAMESPACE, DEFAULT_NAME);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected XMLTextNodeImpl constructElement(String namespace, String name) {
		return new XMLTextNodeImpl(namespace, name, DEFAULT_VALUE);
	}

/**
	 * Tests that the value merely shows up in the node between the '>' and '<' symbols.
	 */
	@Test
	public void testToStringValue() {
		String asString = getXMLObject().toString();
		assertTrue(asString, asString.contains(">" + DEFAULT_VALUE + "<"));
	}

	/**
	 * Tests that a basic value toStrings to the expected format - this simply
	 * nullifies the namespace as that is tested in {@link XMLElementTest}.
	 */
	@Test
	public void testToString() {
		getXMLObject().setNamespace(null);
		final String expected = "<" + getXMLObject().getName() + ">"
				+ DEFAULT_VALUE + "</" + getXMLObject().getName() + ">";

		assertEquals(expected, getXMLObject().toString());
	}

	@Test
	public void testNullValue() {
		XMLTextNode node = new XMLTextNodeImpl(null, "name", null);
		node.declarePrefix(XMLNode.NS_PREFIX_XSI, XMLNode.NS_URI_XSI);

		assertEquals(
				"<name xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" />",
				node.toString());
	}
}
