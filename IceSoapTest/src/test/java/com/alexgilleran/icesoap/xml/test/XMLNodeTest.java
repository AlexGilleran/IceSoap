/**
 * 
 */
package com.alexgilleran.icesoap.xml.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.alexgilleran.icesoap.xml.XMLElement;
import com.alexgilleran.icesoap.xml.XMLNode;
import com.alexgilleran.icesoap.xml.XMLParentNode;
import com.alexgilleran.icesoap.xml.XMLTextNode;
import com.alexgilleran.icesoap.xml.impl.XMLParentNodeImpl;

/**
 * Tests the inherited functionality of {@link XMLParentNodeImpl} by starting
 * with a populated element.
 * 
 * Note that tests for addition functions like
 * {@link XMLParentNode#addElement(com.alexgilleran.icesoap.xml.XMLNode)} are
 * contained in {@link XMLNodeTestEmpty}.
 * 
 * @author Alex Gilleran
 * 
 */
public class XMLNodeTest extends XMLElementTest<XMLParentNodeImpl> {
	/** Basic namespace to pass up the class hierarchy */
	private final static String DEFAULT_NAMESPACE = "http://www.xmlnode.com/lulzy";
	/** Basic name to pass up the class hierarchy */
	private final static String DEFAULT_NAME = "nodeelement";

	private final static String NAMESPACE_NODE_1 = "http://namespacenode1.com";
	private final static String NAMESPACE_NODE_2 = "http://namespacenode2.com";
	private final static String NAMESPACE_TEXT_NODE = "http://namespacetextnode.com";

	private final static String NAME_NODE_1 = "namenode1";
	private final static String NAME_NODE_2 = "namenode2";
	private final static String NAME_TEXT_NODE = "textnodeName";
	private final static String VALUE_TEXT_NODE = "textnodeName";

	public XMLNodeTest() {
		super(DEFAULT_NAMESPACE, DEFAULT_NAME);
	}

	@Override
	protected XMLParentNodeImpl constructElement(String namespace, String name) {
		XMLParentNodeImpl baseNode = new XMLParentNodeImpl(namespace, name);

		XMLParentNode node1 = baseNode.addParentNode(NAMESPACE_NODE_1,
				NAME_NODE_1);
		node1.addParentNode(NAMESPACE_NODE_2, NAME_NODE_2);

		baseNode.addTextNode(NAMESPACE_TEXT_NODE, NAME_TEXT_NODE,
				VALUE_TEXT_NODE);

		return baseNode;
	}

	/**
	 * Tests the getSubElements function against the data we set up in
	 * constructElement
	 */
	@Test
	public void testGetSubElements() {
		List<XMLElement> baseNodeElements = getXMLObject().getChildNodes();

		assertEquals(2, baseNodeElements.size());

		assertTrue(XMLParentNode.class.isAssignableFrom(baseNodeElements.get(0)
				.getClass()));
		XMLParentNode node1 = (XMLParentNode) baseNodeElements.get(0);
		assertEquals(NAME_NODE_1, node1.getName());
		assertEquals(NAMESPACE_NODE_1, node1.getNamespace());

		List<XMLElement> node1SubElements = node1.getChildNodes();
		assertEquals(1, node1SubElements.size());
		assertTrue(XMLParentNode.class.isAssignableFrom(node1SubElements.get(0)
				.getClass()));
		XMLParentNode node2 = (XMLParentNode) node1SubElements.get(0);
		assertEquals(NAME_NODE_2, node2.getName());
		assertEquals(NAMESPACE_NODE_2, node2.getNamespace());

		assertTrue(XMLTextNode.class.isAssignableFrom(baseNodeElements.get(1)
				.getClass()));
		XMLTextNode textElement = (XMLTextNode) baseNodeElements.get(1);
		assertEquals(NAME_TEXT_NODE, textElement.getName());
		assertEquals(NAMESPACE_TEXT_NODE, textElement.getNamespace());
		assertEquals(VALUE_TEXT_NODE, textElement.getValue());
	}

	/**
	 * Build up a string corresponding to what the basic node should look like
	 * serialized, then compare it.
	 * 
	 * This is a nasty test but it's gotta be here for regression testing.
	 */
	@Test
	public void testToString() {
		final String defaultPrefix = "default";
		final String prefixNode1 = "prefix1";
		final String prefixNode2 = "prefix2";
		final String prefixText = "prefixtext";

		getXMLObject().declarePrefix(defaultPrefix, DEFAULT_NAMESPACE);

		((XMLNode) getXMLObject().getChildNodes().get(0)).declarePrefix(
				prefixNode1, NAMESPACE_NODE_1);
		((XMLNode) ((XMLParentNode) getXMLObject().getChildNodes().get(0))
				.getChildNodes().get(0)).declarePrefix(prefixNode2,
				NAMESPACE_NODE_2);
		((XMLNode) getXMLObject().getChildNodes().get(1)).declarePrefix(
				prefixText, NAMESPACE_TEXT_NODE);

		StringBuilder builder = new StringBuilder();
		builder.append("<").append(defaultPrefix).append(":")
				.append(getXMLObject().getName());
		builder.append(" xmlns:").append(defaultPrefix).append("=\"")
				.append(DEFAULT_NAMESPACE).append("\"");
		builder.append(">");

		builder.append("<").append(prefixNode1).append(":").append(NAME_NODE_1);
		builder.append(" xmlns:").append(prefixNode1).append("=\"")
				.append(NAMESPACE_NODE_1).append("\"");
		builder.append(">");

		builder.append("<").append(prefixNode2).append(":").append(NAME_NODE_2);
		builder.append(" xmlns:").append(prefixNode2).append("=\"")
				.append(NAMESPACE_NODE_2).append("\"");
		builder.append(" />");

		builder.append("</").append(prefixNode1).append(":")
				.append(NAME_NODE_1).append(">");

		builder.append("<").append(prefixText).append(":")
				.append(NAME_TEXT_NODE);
		builder.append(" xmlns:").append(prefixText).append("=\"")
				.append(NAMESPACE_TEXT_NODE).append("\"");
		builder.append(">");

		builder.append(VALUE_TEXT_NODE);

		builder.append("</").append(prefixText).append(":")
				.append(NAME_TEXT_NODE).append(">");

		builder.append("</").append(defaultPrefix).append(":")
				.append(getXMLObject().getName()).append(">");

		assertEquals(builder.toString(), getXMLObject().toString());
	}
}
