package com.alexgilleran.icesoap.parser;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.alexgilleran.icesoap.xpath.elements.XPathElement;
import com.alexgilleran.icesoap.xpath.elements.impl.AttributeXPathElement;
import com.alexgilleran.icesoap.xpath.elements.impl.SingleSlashXPathElement;

/**
 * Wrapper for XMLPullParser for XPath operations.
 * 
 * @author Alex Gilleran
 * 
 */
public class XPathXmlPullParser {
	public static int START_DOCUMENT = XmlPullParser.START_DOCUMENT;
	public static int END_DOCUMENT = XmlPullParser.END_DOCUMENT;
	public static int START_TAG = XmlPullParser.START_TAG;
	public static int END_TAG = XmlPullParser.END_TAG;
	public static int TEXT = XmlPullParser.TEXT;
	public static int ATTRIBUTE = 5;

	private XmlPullParser parser = Xml.newPullParser();
	private XPathElement currentElement;
	private boolean removeLastXPathElement;
	private int currentAttribute = 0;

	public XPathXmlPullParser() {

	}

	/**
	 * 
	 * Get the String value of the current node, whether attribute or text.
	 * 
	 * @return The string value of the current node.
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public String getCurrentValue() throws XmlPullParserException, IOException {
		if (currentElement.isAttribute()) {
			return this.getCurrentAttributeValue();
		} else {
			return parser.nextText();
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

	/**
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 * @see org.xmlpull.v1.XmlPullParser#next()
	 */
	public int next() throws XmlPullParserException, IOException {
		int next;

		if (parser.getEventType() == XmlPullParser.START_TAG
				&& currentAttribute <= parser.getAttributeCount() - 1) {
			// There are attributes here - process them in turn before we get to
			// the value
			next = ATTRIBUTE;

			currentElement = new AttributeXPathElement(
					parser.getAttributeName(currentAttribute), currentElement);

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
			currentElement = new SingleSlashXPathElement(
					parser.getName(), currentElement);

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
	}

	/**
	 * @return the currentXPath
	 */
	public XPathElement getCurrentElement() {
		return currentElement;
	}

	/**
	 * @return
	 * @throws XmlPullParserException
	 * @see org.xmlpull.v1.XmlPullParser#getEventType()
	 */
	public int getEventType() throws XmlPullParserException {
		if (currentAttribute == 0) {
			return parser.getEventType();
		} else {
			return ATTRIBUTE;
		}
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @throws XmlPullParserException
	 * @see org.xmlpull.v1.XmlPullParser#setInput(java.io.InputStream,
	 *      java.lang.String)
	 */
	public void setInput(InputStream arg0, String arg1)
			throws XmlPullParserException {
		parser.setInput(arg0, arg1);
	}

}
