package com.alexgilleran.icesoap.parser.impl;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.alexgilleran.icesoap.exception.XmlParsingException;
import com.alexgilleran.icesoap.parser.XPathPullParser;
import com.alexgilleran.icesoap.xpath.elements.XPathElement;
import com.alexgilleran.icesoap.xpath.elements.impl.AttributeXPathElement;
import com.alexgilleran.icesoap.xpath.elements.impl.SingleSlashXPathElement;

/**
 * Wrapper for XMLPullParser for XPath operations.
 * 
 * @author Alex Gilleran
 * 
 */
public class XPathPullParserImpl implements XPathPullParser {
	private XmlPullParser parser = PullParserFactory.getInstance()
			.buildParser();
	private XPathElement currentElement;
	private boolean removeLastXPathElement;
	private int currentAttribute = 0;

	public XPathPullParserImpl() {

	}

	@Override
	public String getCurrentValue() throws XmlParsingException {
		try {
			if (currentElement.isAttribute()) {
				return this.getCurrentAttributeValue();
			} else {
				return parser.nextText();

			}
		} catch (XmlPullParserException e) {
			throw new XmlParsingException(e);
		} catch (IOException e) {
			throw new XmlParsingException(e);
		}
	}

	/**
	 * 
	 * @return The value of the attribute currently pointed at - if there is no
	 *         current attribute, null.
	 */
	private String getCurrentAttributeValue() {
		if ((parser.getAttributeCount() - 1) >= (currentAttribute - 1)) {
			return parser.getAttributeValue(currentAttribute - 1);
		} else {
			return null;
		}
	}

	@Override
	public int next() throws XmlParsingException {
		int next;

		try {
			if (parser.getEventType() == XmlPullParser.START_TAG
					&& currentAttribute <= parser.getAttributeCount() - 1) {
				// There are attributes here - process them in turn before we
				// get to
				// the value
				next = XPathPullParser.ATTRIBUTE;

				currentElement = new AttributeXPathElement(
						parser.getAttributeName(currentAttribute),
						currentElement);

				currentAttribute++;
			} else {
				next = parser.next();

				if (currentElement.isAttribute()) {
					currentElement = currentElement.getPreviousElement();
				}

				currentAttribute = 0;
			}

			if (removeLastXPathElement) {
				currentElement = currentElement.getPreviousElement();
				removeLastXPathElement = false;
			}

			switch (getEventType()) {
			case XmlPullParser.START_TAG:// TODO:
				currentElement = new SingleSlashXPathElement(parser.getName(),
						currentElement);

				int attributeCount = parser.getAttributeCount();
				if (attributeCount > 0) {
					for (int i = 0; i < attributeCount; i++) {
						currentElement.addPredicate(parser.getAttributeName(i),
								parser.getAttributeValue(i));
					}
				}

				break;
			case XmlPullParser.END_TAG:
				removeLastXPathElement = true;
				break;
			}

			return next;
		} catch (XmlPullParserException e) {
			throw new XmlParsingException(e);
		} catch (IOException e) {
			throw new XmlParsingException(e);
		}
	}

	@Override
	public XPathElement getCurrentElement() {
		return currentElement;
	}

	@Override
	public int getEventType() throws XmlPullParserException {
		if (currentAttribute == 0) {
			return parser.getEventType();
		} else {
			return XPathPullParser.ATTRIBUTE;
		}
	}

	@Override
	public void setInput(InputStream arg0, String arg1)
			throws XmlPullParserException {
		parser.setInput(arg0, arg1);
	}

}
