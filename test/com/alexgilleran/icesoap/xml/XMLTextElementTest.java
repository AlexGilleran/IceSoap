/**
 * 
 */
package com.alexgilleran.icesoap.xml;

import com.alexgilleran.icesoap.xml.impl.XMLTextElement;

/**
 * @author Alex Gilleran
 * 
 */
public class XMLTextElementTest extends XMLElementTest<XMLTextElement> {
	private final static String NAMESPACE = "http://www.example.com";
	private final static String NAME = "textelement";

	/**
	 * @param namespace
	 * @param name
	 */
	public XMLTextElementTest() {
		super(NAMESPACE, NAME);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.alexgilleran.icesoap.xml.XMLObjectTest#constructObject(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	protected XMLTextElement constructObject(String namespace, String name) {
		return new XMLTextElement(NAMESPACE, NAME, "value");
	}

}
