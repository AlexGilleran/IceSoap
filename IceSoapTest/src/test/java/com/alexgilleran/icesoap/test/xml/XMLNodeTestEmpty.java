/**
 * 
 */
package com.alexgilleran.icesoap.test.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.alexgilleran.icesoap.xml.impl.XMLNodeImpl;
import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * Tests the functionality of {@link XMLNodeImpl}, starting with a blank slate,
 * rather than the pre-populated state of {@link XMLNodeTest}.
 * 
 * @author Alex Gilleran
 * 
 */
@RunWith(RobolectricTestRunner.class)
public class XMLNodeTestEmpty extends XMLElementTest<XMLNodeImpl> {
	/** Basic namespace to pass up the class hierarchy */
	private final static String DEFAULT_NAMESPACE = "http://www.xmlnodeempty.com/lulzy";
	/** Basic name to pass up the class hierarchy */
	private final static String DEFAULT_NAME = "emptynodeelement";

	public XMLNodeTestEmpty() {
		super(DEFAULT_NAMESPACE, DEFAULT_NAME);
	}

	@Override
	protected XMLNodeImpl constructElement(String namespace, String name) {
		return new XMLNodeImpl(namespace, name);
	}

	@Test
	public void testAddNode() {

	}

	@Test
	public void testAddElement() {

	}

	@Test
	public void testAddTextElement() {

	}

	/**
	 * Test the that the basic XMLElement toStrings as we expect - this has to
	 * be overridden for empty nodes.
	 */
	@Test
	@Override
	public void testToStringBasic() {
		getXMLObject().setNamespace(null);

		String asString = getXMLObject().toString();

		assertEquals(asString, "<" + getXMLObject().getName() + " />");
	}

	/**
	 * Same as {@link XMLNodeTestEmpty#testToStringBasic()}, but takes into
	 * account a namespace.
	 */
	@Test
	@Override
	public void testToStringBasicWithNamespace() {
		final String prefix = "prefix";

		// First declare the namespace's prefix so we can test that too
		getXMLObject().declarePrefix(prefix, getXMLObject().getNamespace());

		String asString = getXMLObject().toString();

		assertEquals(asString, "<" + prefix + ":" + getXMLObject().getName()
				+ " xmlns:" + prefix + "=\"" + getXMLObject().getNamespace()
				+ "\" />");
	}

	@Test
	public void testEmpty() {
		assertTrue(getXMLObject().getSubElements().isEmpty());
	}
}
