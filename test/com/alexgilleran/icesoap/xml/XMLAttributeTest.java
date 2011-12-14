/**
 * 
 */
package com.alexgilleran.icesoap.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.alexgilleran.icesoap.xml.impl.XMLAttributeImpl;

/**
 * @author Alex Gilleran
 * 
 */
public class XMLAttributeTest extends XMLObjectTest<XMLAttributeImpl> {
	private final static String NAMESPACE = "http://namespace.com/namespace";
	private final static String NAME = "xmlAttName";
	private final static String VALUE = "xmlValue";

	public XMLAttributeTest() {
		super(NAMESPACE, NAME);
	}

	@Override
	protected XMLAttributeImpl constructObject(String namespace, String name) {
		return new XMLAttributeImpl(namespace, name, VALUE);
	}

	@Test
	@Override
	public void testConstructor() {
		super.testConstructor();

		assertEquals(VALUE, getXmlObject().getValue());
	}

	@Test
	public void testGetSetValue() {
		final String anotherValue = "anotherValue";

		assertFalse(anotherValue.equals(getXmlObject().getValue()));

		getXmlObject().setValue(anotherValue);

		assertEquals(anotherValue, getXmlObject().getValue());
	}

}
