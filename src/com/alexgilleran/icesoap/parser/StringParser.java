package com.alexgilleran.icesoap.parser;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

public class StringParser extends BaseParser<String> {
	String elementToGet;

	public StringParser(String rootElementName, String elementToGet) {
		super(rootElementName);

		this.elementToGet = elementToGet;
	}

	@Override
	protected String parseTag(String tagName, XPathXmlPullParser parser,
			String string) throws XmlPullParserException, IOException {
		if (tagName.equalsIgnoreCase(elementToGet)) {
			return parser.nextText();
		} else {
			return string;
		}
	}

	@Override
	public String initializeParsedObject() {
		return null;
	}

}
