/**
 * 
 */
package com.alexgilleran.icesoap.xml.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.alexgilleran.icesoap.xml.impl.XMLObjectBase;
import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * Tests the basic functionality of the {@link XMLObjectBase} class.
 * 
 * @author Alex Gilleran
 * 
 */
@RunWith(RobolectricTestRunner.class)
public abstract class XMLObjectTest<TypeUnderTest extends XMLObjectBase> {
	/**
	 * The namespace of the object, to be passed back to the extending class
	 * when instantiating new objects
	 */
	private String namespace;
	/**
	 * The name of the object, to be passed back to the extending class when
	 * instantiating new objects
	 */
	private String name;
	/**
	 * The current instance of the extending XmlObject - this is reset on @Before
	 */
	private TypeUnderTest xmlObject;

	public XMLObjectTest(String namespace, String name) {
		this.namespace = namespace;
		this.name = name;
	}

	/**
	 * Reinitializes the instance of the class under test at the start of each
	 * test.
	 */
	@Before
	public void reinitializeObject() {
		xmlObject = constructObject(namespace, name);
	}

	/**
	 * Abstract method for constructing a new instance of the XML Object under
	 * test. Objects returned by this method must set the passed namespace and
	 * name strings.
	 * 
	 * @param namespace
	 *            The namespace of the new object.
	 * @param name
	 *            The name to set in the new object.
	 * @return A new instance of the type under test.
	 */
	protected abstract TypeUnderTest constructObject(String namespace, String name);

	/**
	 * Returns the current instance of the XML Object type under test - resets
	 * at the beginning of every test
	 */
	protected TypeUnderTest getXMLObject() {
		return xmlObject;
	}

	/**
	 * Tests that the standard namespace/name constructor for
	 * {@link XMLObjectBase} works as per contract.
	 */
	@Test
	public void testConstructor() {
		assertEquals(namespace, xmlObject.getNamespace());
		assertEquals(name, xmlObject.getName());
	}

	/**
	 * Test that the namespace can be get and set normally.
	 */
	@Test
	public void testGetSetNamespace() {
		final String differentNamespace = namespace + "/different";

		assertFalse(differentNamespace.equals(xmlObject.getNamespace()));

		xmlObject.setNamespace(differentNamespace);

		assertEquals(differentNamespace, xmlObject.getNamespace());
	}

	/**
	 * Tests that the name can be get and set normally.
	 */
	@Test
	public void testGetSetName() {
		final String differentName = name + "different";

		assertFalse(differentName.equals(xmlObject.getName()));

		xmlObject.setName(differentName);

		assertEquals(differentName, xmlObject.getName());
	}
}
