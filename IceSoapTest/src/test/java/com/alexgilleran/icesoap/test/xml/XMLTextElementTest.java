/**
 * 
 */
package com.alexgilleran.icesoap.test.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.alexgilleran.icesoap.xml.impl.XMLTextElement;
import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * Tests that the {@link XMLTextElement} class, which represents basic XML Text
 * nodes in the format {@code <element>value</element>}, works as desired.
 * 
 * @author Alex Gilleran
 * 
 */
@RunWith(RobolectricTestRunner.class)
public class XMLTextElementTest extends XMLElementTest<XMLTextElement> {
	/** Basic namespace to pass up the class hierarchy */
	private final static String DEFAULT_NAMESPACE = "http://www.example.com";
	/** Basic name to pass up the class hierarchy */
	private final static String DEFAULT_NAME = "textelement";
	/** Basic text value */
	private static final String DEFAULT_VALUE = "value";

	public XMLTextElementTest() {
		super(DEFAULT_NAMESPACE, DEFAULT_NAME);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected XMLTextElement constructElement(String namespace, String name) {
		return new XMLTextElement(namespace, name, DEFAULT_VALUE);
	}

/**
	 * Tests that the value merely shows up in the node between the '>' and '<' symbols.
	 */
	@Test
	public void testToStringValue() {
		String asString = getXmlObject().toString();
		assertTrue(asString, asString.contains(">" + DEFAULT_VALUE + "<"));
	}

	/**
	 * Tests that a basic value toStrings to the expected format - this simply
	 * nullifies the namespace as that is tested in {@link XMLElementTest}.
	 */
	@Test
	public void testToString() {
		getXmlObject().setNamespace(null);
		final String expected = "<" + getXmlObject().getName() + ">"
				+ DEFAULT_VALUE + "</" + getXmlObject().getName() + ">";

		assertEquals(expected, getXmlObject().toString());
	}
}
