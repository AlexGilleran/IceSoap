package com.alexgilleran.icesoap.xml.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

import org.xmlpull.v1.XmlSerializer;

import com.alexgilleran.icesoap.xml.XMLElement;

public class XMLNode extends XMLElementBase {
	private Collection<XMLElementBase> subElements;
	
	public XMLNode(String namespace, String name) {
		super(namespace, name);
		
		subElements = new LinkedList<XMLElementBase>();
	}
	
	public Collection<XMLElementBase> getSubElements() {
		return subElements;
	}

	public XMLNode addElement(String namespace, String name) {
		XMLNode newNode = new XMLNode(namespace, name);
		
		subElements.add(newNode);
		
		return newNode;
	}
	
	public XMLElement addElement(XMLElementBase element) {
		subElements.add(element);
		
		return element;
	}
	
	public XMLTextElement addElement(String namespace, String name, String value) {
		XMLTextElement newLeaf = new XMLTextElement(namespace, name, value);
		
		subElements.add(newLeaf);
		
		return newLeaf;
	}
	
	@Override
	protected void serializeContent(XmlSerializer cereal) throws IllegalArgumentException, IllegalStateException, IOException {
		for (XMLElement element: subElements) {
			element.serialize(cereal);
		}
	}
}
