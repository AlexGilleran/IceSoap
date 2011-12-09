package com.alexgilleran.icesoap.xml;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.xmlpull.v1.XmlSerializer;

import com.alexgilleran.icesoap.envelope.SOAPEnv;


public abstract class XMLElement {
	private Map<String, String> declaredPrefixes;
	private String namespace;
	private Set<XMLAttribute> attributes;
	private String name;

	public XMLElement(String namespace, String name) {
		this.name = name;
		this.namespace = namespace;

		declaredPrefixes = new HashMap<String, String>();
		attributes = new HashSet<XMLAttribute>();
	}

	public Collection<XMLAttribute> getAttributes() {
		return attributes;
	}

	public void addAttribute(String namespace, String name, String value) {
		XMLAttribute att = new XMLAttribute(namespace, name, value);
		attributes.add(att);
	}

	public void setType(String type) {
		this.addAttribute(SOAPEnv.NS_URI_XSI, "type", type);
	}

	public void declarePrefix(String prefix, String namespace) {
		declaredPrefixes.put(prefix, namespace);
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public void serialize(XmlSerializer cereal)
			throws IllegalArgumentException, IllegalStateException, IOException {
		serializePrefixes(cereal);

		cereal.startTag(namespace, name);
		serializeAttributes(cereal);

		serializeContent(cereal);

		cereal.endTag(namespace, name);
	}

	protected void serializeAttributes(XmlSerializer cereal)
			throws IllegalArgumentException, IllegalStateException, IOException {
		for (XMLAttribute attribute : attributes) {
			cereal.attribute(attribute.getNamespace(), attribute.getName(),
					attribute.getValue());
		}
	}

	protected void serializePrefixes(XmlSerializer cereal)
			throws IllegalArgumentException, IllegalStateException, IOException {
		for (String key : declaredPrefixes.keySet()) {
			cereal.setPrefix(key, declaredPrefixes.get(key));
		}
	}

	protected abstract void serializeContent(XmlSerializer cereal)
			throws IllegalArgumentException, IllegalStateException, IOException;
}
