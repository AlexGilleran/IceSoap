/**
 * 
 */
package com.alexgilleran.icesoap.xml.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.alexgilleran.icesoap.xml.XMLNode;
import com.alexgilleran.icesoap.xml.XMLTextElement;
import com.alexgilleran.icesoap.xml.impl.XMLNodeImpl;
import com.alexgilleran.icesoap.xml.impl.XMLTextElementImpl;

/**
 * Tests the functionality of {@link XMLNodeImpl}, starting with a blank slate,
 * rather than the pre-populated state of {@link XMLNodeTest}.
 * 
 * @author Alex Gilleran
 * 
 */
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
		final String namespace1 = "http://namespace1";
		final String name1 = "name1";
		final String namespace2 = "http://namespace2";
		final String name2 = "name2";

		// Check that the object is empty
		assertTrue(getXMLObject().getSubElements().isEmpty());

		// Add a node
		getXMLObject().addNode(namespace1, name1);

		// Assert that there's now one object
		assertEquals(1, getXMLObject().getSubElements().size());

		// Check that the details have gone in as expected
		assertTrue(XMLNode.class.isAssignableFrom(getXMLObject()
				.getSubElements().get(0).getClass()));
		assertEquals(name1, getXMLObject().getSubElements().get(0).getName());
		assertEquals(namespace1, getXMLObject().getSubElements().get(0)
				.getNamespace());

		// Add another node
		getXMLObject().addNode(namespace2, name2);

		// Assert that there are now two elements
		assertEquals(2, getXMLObject().getSubElements().size());

		// Check that the details of the first element is still correct
		assertTrue(XMLNode.class.isAssignableFrom(getXMLObject()
				.getSubElements().get(0).getClass()));
		assertEquals(name1, getXMLObject().getSubElements().get(0).getName());
		assertEquals(namespace1, getXMLObject().getSubElements().get(0)
				.getNamespace());

		// Check that the details of the new element are correct
		assertTrue(XMLNode.class.isAssignableFrom(getXMLObject()
				.getSubElements().get(1).getClass()));
		assertEquals(name2, getXMLObject().getSubElements().get(1).getName());
		assertEquals(namespace2, getXMLObject().getSubElements().get(1)
				.getNamespace());
	}

	@Test
	public void testAddElement() {
		final String namespace1 = "http://namespace1";
		final String name1 = "name1";
		final String namespace2 = "http://namespace2";
		final String name2 = "name2";
		final String value2 = "value2";

		// Check that the object is empty
		assertTrue(getXMLObject().getSubElements().isEmpty());

		// Add a node
		getXMLObject().addElement(new XMLNodeImpl(namespace1, name1));

		// Assert that there's now one object
		assertEquals(1, getXMLObject().getSubElements().size());

		// Check that the details have gone in as expected
		assertTrue(XMLNode.class.isAssignableFrom(getXMLObject()
				.getSubElements().get(0).getClass()));
		assertEquals(name1, getXMLObject().getSubElements().get(0).getName());
		assertEquals(namespace1, getXMLObject().getSubElements().get(0)
				.getNamespace());

		// Add a text element
		getXMLObject().addElement(
				new XMLTextElementImpl(namespace2, name2, value2));

		// Assert that there are now two elements
		assertEquals(2, getXMLObject().getSubElements().size());

		// Check that the details of the first element is still correct
		assertTrue(XMLNode.class.isAssignableFrom(getXMLObject()
				.getSubElements().get(0).getClass()));
		assertEquals(name1, getXMLObject().getSubElements().get(0).getName());
		assertEquals(namespace1, getXMLObject().getSubElements().get(0)
				.getNamespace());

		// Check that the details of the new element are correct
		assertTrue(XMLTextElement.class.isAssignableFrom(getXMLObject()
				.getSubElements().get(1).getClass()));
		assertEquals(name2, getXMLObject().getSubElements().get(1).getName());
		assertEquals(namespace2, getXMLObject().getSubElements().get(1)
				.getNamespace());
		assertEquals(value2, ((XMLTextElement) getXMLObject().getSubElements()
				.get(1)).getValue());
	}

	@Test
	public void testAddTextElement() {
		final String namespace1 = "http://namespace1";
		final String name1 = "name1";
		final String value1 = "value1";
		final String namespace2 = "http://namespace2";
		final String name2 = "name2";
		final String value2 = "value2";

		// Check that the object is empty
		assertTrue(getXMLObject().getSubElements().isEmpty());

		// Add a text element
		getXMLObject().addTextElement(namespace1, name1, value1);

		// Assert that there's now one object
		assertEquals(1, getXMLObject().getSubElements().size());

		// Check that the details have gone in as expected
		assertTrue(XMLTextElement.class.isAssignableFrom(getXMLObject()
				.getSubElements().get(0).getClass()));
		assertEquals(name1, getXMLObject().getSubElements().get(0).getName());
		assertEquals(namespace1, getXMLObject().getSubElements().get(0)
				.getNamespace());
		assertEquals(value1, ((XMLTextElement) getXMLObject().getSubElements()
				.get(0)).getValue());

		// Add another text element
		getXMLObject().addTextElement(namespace2, name2, value2);

		// Assert that there are now two elements
		assertEquals(2, getXMLObject().getSubElements().size());

		// Check that the details of the first element is still correct
		assertTrue(XMLTextElement.class.isAssignableFrom(getXMLObject()
				.getSubElements().get(0).getClass()));
		assertEquals(name1, getXMLObject().getSubElements().get(0).getName());
		assertEquals(namespace1, getXMLObject().getSubElements().get(0)
				.getNamespace());
		assertEquals(value1, ((XMLTextElement) getXMLObject().getSubElements()
				.get(0)).getValue());

		// Check that the details of the new element are correct
		assertTrue(XMLTextElement.class.isAssignableFrom(getXMLObject()
				.getSubElements().get(1).getClass()));
		assertEquals(name2, getXMLObject().getSubElements().get(1).getName());
		assertEquals(namespace2, getXMLObject().getSubElements().get(1)
				.getNamespace());
		assertEquals(value2, ((XMLTextElement) getXMLObject().getSubElements()
				.get(1)).getValue());
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

	/**
	 * Test that the new node is empty of subelements
	 */
	@Test
	public void testEmpty() {
		assertTrue(getXMLObject().getSubElements().isEmpty());
	}
}
