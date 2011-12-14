/**
 * 
 */
package com.alexgilleran.icesoap.xml;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.alexgilleran.icesoap.envelope.SOAPEnv;
import com.alexgilleran.icesoap.xml.impl.XMLAttributeImpl;
import com.alexgilleran.icesoap.xml.impl.XMLElementBase;

/**
 * @author Alex Gilleran
 * 
 */
public abstract class XMLElementTest<T extends XMLElementBase> extends
		XMLObjectTest<T> {
	public XMLElementTest(String namespace, String name) {
		super(namespace, name);
	}

	@Test
	public void testGetAddAttributes() {
		// Assert that there are no attributes to start with
		assertEquals(0, getXmlObject().getAttributes().size());

		// Make a set of sample data
		Set<XMLAttribute> attributes = new HashSet<XMLAttribute>();
		attributes
				.add(new XMLAttributeImpl("http://ns1.com", "name1", "value1"));
		attributes
				.add(new XMLAttributeImpl("http://ns2.com", "name2", "value2"));
		attributes
				.add(new XMLAttributeImpl("http://ns3.com", "name3", "value3"));

		// Add the same values from the sample data to the XML Object
		getXmlObject().addAttribute("http://ns1.com", "name1", "value1");
		getXmlObject().addAttribute("http://ns2.com", "name2", "value2");
		getXmlObject().addAttribute("http://ns3.com", "name3", "value3");

		// Make sure they all went in okay
		assertEquals(3, getXmlObject().getAttributes().size());

		// Make sure the actual attributes match the sample ones
		assertEquals(attributes, getXmlObject());
		
		System.out.println(getXmlObject().toString());
	}

	@Test
	public void testSetType() {
		final String type = "thisisatype";
		final String anotherType = "anotherType";

		// Make sure there's no attributes to start with
		assertEquals(0, getXmlObject().getAttributes().size());

		// Set the type - this will create an attribute for xsi:type
		getXmlObject().setType(type);

		// Now get the attributes
		Collection<XMLAttribute> attributes = getXmlObject().getAttributes();
		assertEquals(1, attributes.size());
		XMLAttribute typeAtt = attributes.iterator().next();

		// Ensure the attribute is correct
		assertEquals(type, typeAtt.getValue());
		assertEquals(SOAPEnv.NS_URI_XSI, typeAtt.getNamespace());
		assertEquals("type", typeAtt.getName());

		// Set the type again
		getXmlObject().setType(anotherType);

		// Get the type attribute again
		attributes = getXmlObject().getAttributes();
		assertEquals(1, attributes.size());
		typeAtt = attributes.iterator().next();

		// Ensure the attribute is still correct and has the new value
		assertEquals(anotherType, typeAtt.getValue());
		assertEquals(SOAPEnv.NS_URI_XSI, typeAtt.getNamespace());
		assertEquals("type", typeAtt.getName());
	}
	//
	// @Test
	// public void testDeclarePrefix()
	// {
	// getXmlObject().
	// }
	//
	// protected String getAsString

}
