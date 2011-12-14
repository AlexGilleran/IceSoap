/**
 * 
 */
package com.alexgilleran.icesoap.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import com.alexgilleran.icesoap.xml.impl.XMLObjectBase;

/**
 * @author Alex Gilleran
 * 
 */
public abstract class XMLObjectTest<T extends XMLObjectBase> {

	private String namespace;
	private String name;
	private T xmlObject;

	public XMLObjectTest(String namespace, String name) {
		this.namespace = namespace;
		this.name = name;
	}

	@Before
	public void setUp() {
		xmlObject = constructObject(namespace, name);
	}

	protected abstract T constructObject(String namespace, String name);

	protected T getXmlObject() {
		return xmlObject;
	}

	@Test
	public void testConstructor() {
		assertEquals(namespace, xmlObject.getNamespace());
		assertEquals(name, xmlObject.getName());
	}

	@Test
	public void testGetNamespace() {
		final String differentNamespace = namespace + "/different";

		assertFalse(differentNamespace.equals(xmlObject.getNamespace()));

		xmlObject.setNamespace(differentNamespace);

		assertEquals(differentNamespace, xmlObject.getNamespace());
	}

	@Test
	public void testGetSetName() {
		final String differentName = name + "different";

		assertFalse(differentName.equals(xmlObject.getName()));

		xmlObject.setName(differentName);

		assertEquals(differentName, xmlObject.getName());
	}
}
