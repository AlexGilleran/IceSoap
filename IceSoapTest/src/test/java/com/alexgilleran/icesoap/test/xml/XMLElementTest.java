/**
 * 
 */
package com.alexgilleran.icesoap.test.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import com.alexgilleran.icesoap.xml.XMLAttribute;
import com.alexgilleran.icesoap.xml.XMLElement;
import com.alexgilleran.icesoap.xml.XMLSerializerFactory;
import com.alexgilleran.icesoap.xml.impl.XMLAttributeImpl;
import com.alexgilleran.icesoap.xml.impl.XMLElementBase;

/**
 * Runs a number of tests on the XMLElement object, asserting that the base code
 * works as intended, and that any extending classes implement its methods
 * according to contract.
 * 
 * @author Alex Gilleran
 * 
 */
public abstract class XMLElementTest<TypeUnderTest extends XMLElementBase>
		extends XMLObjectTest<TypeUnderTest> {
	public XMLElementTest(String namespace, String name) {
		super(namespace, name);
	}

	/**
	 * Constructs a new object - this simply delegates to the extending class,
	 * but changes the serializer factory to make it compatible with
	 * roboelectric junits.
	 */
	@Override
	protected TypeUnderTest constructObject(String namespace, String name) {
		TypeUnderTest element = constructElement(namespace, name);

		element.setSerializerFactory(JUNIT_SERIALIZER_FACTORY);

		return element;
	}

	/**
	 * Constructs a new class that extends {@link XMLElementBase}. The object
	 * that is returned from this method should:
	 * 
	 * <ul>
	 * <li>Use the namespace and name passed</li>
	 * <li>Not declare any prefixes</li>
	 * <li>Not have any attributes</li>
	 * </ul>
	 * 
	 * Having data within the element itself is fine.
	 * 
	 * @param namespace
	 *            The namespace to set on the new {@link XMLElementBase}
	 * @param name
	 *            The name to set on the new {@link XMLElementBase}
	 * @return An instance of an {@link XMLElementBase} extending class.
	 */
	protected abstract TypeUnderTest constructElement(String namespace,
			String name);

	/**
	 * Test the that the basic XMLElement toStrings as we expect.
	 */
	@Test
	public void testToStringBasic() {
		getXMLObject().setNamespace(null);

		String asString = getXMLObject().toString();

		assertTrue(asString,
				asString.startsWith("<" + getXMLObject().getName() + ">"));
		assertTrue(asString,
				asString.endsWith("</" + getXMLObject().getName() + ">"));
	}

	/**
	 * Same as {@link XMLElementTest#testToStringBasic()}, but takes into
	 * account a namespace.
	 */
	@Test
	public void testToStringBasicWithNamespace() {
		final String prefix = "prefix";

		// First declare the namespace's prefix so we can test that too
		getXMLObject().declarePrefix(prefix, getXMLObject().getNamespace());

		String asString = getXMLObject().toString();

		assertTrue(
				asString,
				asString.startsWith("<" + prefix + ":"
						+ getXMLObject().getName() + " xmlns:" + prefix + "=\""
						+ getXMLObject().getNamespace() + "\">"));
		assertTrue(
				asString,
				asString.endsWith("</" + prefix + ":"
						+ getXMLObject().getName() + ">"));
	}

	/**
	 * Test that attributes can be added as expected.
	 */
	@Test
	public void testGetAddAttributes() {
		String testNsUrl1 = "http://ns1.com";
		String testNsPrefix1 = "ns1";
		String testName1 = "name1";
		String testValue1 = "value1";

		String testNsUrl2 = "http://ns2.com";
		String testNsPrefix2 = "ns2";
		String testName2 = "name2";
		String testValue2 = "value2";

		String testNsUrl3 = "http://ns3.com";
		String testNsPrefix3 = "ns3";
		String testName3 = "name3";
		String testValue3 = "value3";

		// Assert that there are no attributes to start with
		assertEquals(0, getXMLObject().getAttributes().size());

		// Make a set of sample data
		Set<XMLAttribute> attributes = new HashSet<XMLAttribute>();
		attributes.add(new XMLAttributeImpl(testNsUrl1, testName1, testValue1));
		attributes.add(new XMLAttributeImpl(testNsUrl2, testName2, testValue2));
		attributes.add(new XMLAttributeImpl(testNsUrl3, testName3, testValue3));

		// Add the same values from the sample data to the XML Object
		getXMLObject().declarePrefix(testNsPrefix1, testNsUrl1);
		getXMLObject().addAttribute(testNsUrl1, testName1, testValue1);
		getXMLObject().declarePrefix(testNsPrefix2, testNsUrl2);
		getXMLObject().addAttribute(testNsUrl2, testName2, testValue2);
		getXMLObject().declarePrefix(testNsPrefix3, testNsUrl3);
		getXMLObject().addAttribute(testNsUrl3, testName3, testValue3);

		// Make sure they all went in okay
		assertEquals(3, getXMLObject().getAttributes().size());

		// Make sure the actual attributes match the sample ones
		assertEquals(attributes, getXMLObject().getAttributes());

		// Make sure the attributes are toStringing properly
		String asString = getXMLObject().toString();

		assertTrue(
				asString,
				asString.contains(testNsPrefix1 + ":" + testName1 + "=\""
						+ testValue1 + "\""));
		assertTrue(
				asString,
				asString.contains(testNsPrefix2 + ":" + testName2 + "=\""
						+ testValue2 + "\""));
		assertTrue(
				asString,
				asString.contains(testNsPrefix3 + ":" + testName3 + "=\""
						+ testValue3 + "\""));
	}

	/**
	 * Test that the type can be set as expected, and that setting the type
	 * subsequent times changes the existing type rather than adding multiple
	 * type declarations.
	 */
	@Test
	public void testSetType() {
		final String typeName = "type";
		final String type = "thisisatype";
		final String anotherType = "anotherType";

		// Make sure there's no attributes to start with
		assertEquals(0, getXMLObject().getAttributes().size());

		// Declare the xsi namespace
		getXMLObject().declarePrefix(XMLElement.NS_PREFIX_XSI,
				XMLElement.NS_URI_XSI);

		// Set the type - this will create an attribute for xsi:type
		getXMLObject().setType(type);

		// Now get the attributes
		Collection<XMLAttribute> attributes = getXMLObject().getAttributes();
		assertEquals(1, attributes.size());
		XMLAttribute typeAtt = attributes.iterator().next();

		// Ensure the attribute is correct
		assertEquals(type, typeAtt.getValue());
		assertEquals(XMLElement.NS_URI_XSI, typeAtt.getNamespace());
		assertEquals(typeName, typeAtt.getName());

		// Test the type is toStringing properly
		String asString = getXMLObject().toString();
		assertTrue(
				asString,
				asString.contains(XMLElement.NS_PREFIX_XSI + ":" + typeName
						+ "=\"" + type + "\""));

		// Set the type again
		getXMLObject().setType(anotherType);

		// Get the type attribute again
		attributes = getXMLObject().getAttributes();
		assertEquals(1, attributes.size());
		typeAtt = attributes.iterator().next();

		// Ensure the attribute is still correct and has the new value
		assertEquals(anotherType, typeAtt.getValue());
		assertEquals(XMLElement.NS_URI_XSI, typeAtt.getNamespace());
		assertEquals(typeName, typeAtt.getName());

		asString = getXMLObject().toString();
		assertTrue(asString.contains(XMLElement.NS_PREFIX_XSI + ":" + typeName
				+ "=\"" + anotherType + "\""));
	}

	/**
	 * Test that prefixes can be declared and overrides the default prefix
	 * creation done by the XmlSerializer.
	 */
	@Test
	public void testDeclarePrefix() {
		String prefixName1 = "prefix";
		String prefixName2 = "prefix2";
		String testNamespace2 = "http://www.prefix2.com";
		getXMLObject()
				.declarePrefix(prefixName1, getXMLObject().getNamespace());
		getXMLObject().declarePrefix(prefixName2, testNamespace2);

		String asString = getXMLObject().toString();

		// There's no method for getting prefixes, so look at the text output.
		assertTrue(asString.contains("<" + prefixName1 + ":"
				+ getXMLObject().getName()));
		assertTrue(asString.contains(prefixName1 + "=\""
				+ getXMLObject().getNamespace() + "\""));
		assertTrue(asString.contains(prefixName2 + "=\"" + testNamespace2
				+ "\""));
	}

	/**
	 * Constructs a standard, non-Android XmlPull serializer that can be used in
	 * Robolectric junits.
	 */
	private static final XMLSerializerFactory JUNIT_SERIALIZER_FACTORY = new XMLSerializerFactory() {
		@Override
		public XmlSerializer buildXmlSerializer() {
			try {
				return XmlPullParserFactory.newInstance().newSerializer();
			} catch (XmlPullParserException e) {
				throw new RuntimeException(e);
			}
		}
	};
}
