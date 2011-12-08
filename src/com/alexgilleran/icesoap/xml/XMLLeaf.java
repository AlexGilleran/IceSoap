package com.alexgilleran.icesoap.xml;

import java.io.IOException;

import org.xmlpull.v1.XmlSerializer;

public class XMLLeaf extends XMLElement {
	private String value;

	public XMLLeaf(String namespace, String name, String value) {
		super(namespace, name);

		if (value == null) {
			this.value = "";
		} else {
			this.value = value;
		}
	}

	public String getValue() {
		return value;
	}

	@Override
	protected void serializeContent(XmlSerializer cereal)
			throws IllegalArgumentException, IllegalStateException, IOException {
		cereal.text(value);
	}
}
