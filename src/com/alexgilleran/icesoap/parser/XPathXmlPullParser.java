package com.alexgilleran.icesoap.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.alexgilleran.icesoap.xpath.XPath;
import com.alexgilleran.icesoap.xpath.XPathElement;

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
	private XPath currentXPath = new XPath();
	private boolean removeLastXPathElement;
	private int currentAttribute = 0;

	public XPathXmlPullParser() {

	}

	// TODO: More professional/less fun javadoc comment
	/**
	 * ONLY USE THIS IF YOU'RE SURE THERE'S A TEXT NODE NEXT OR YOU'RE ON AN
	 * ATTRIBUTE, OTHERWISE I'M NOT ENTIRELY SURE WHAT'LL HAPPEN BUT I'M SURE
	 * IT'LL BE BAD!!11
	 * 
	 * Get the String value of the current node, whether attribute or text.
	 * 
	 * @return The string value of the current node.
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public String getCurrentValue() throws XmlPullParserException, IOException {
		if (currentXPath.targetsAttribute()) {
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
			next = ATTRIBUTE;

			currentXPath.getLastElement().setAttribute(
					parser.getAttributeName(currentAttribute));

			currentAttribute++;
		} else {
			next = parser.next();

			if (currentXPath.getLastElement() != null) {
				currentXPath.getLastElement().setAttribute(null);
			}

			currentAttribute = 0;
		}

		if (removeLastXPathElement) {
			currentXPath.removeElement();
			removeLastXPathElement = false;
		}

		switch (getEventType()) {
		case XmlPullParser.START_TAG:
			XPathElement currentElement = new XPathElement(parser.getName());

			int attributeCount = parser.getAttributeCount();
			if (attributeCount > 0) {
				for (int i = 0; i < attributeCount; i++) {
					currentElement.addPredicate(parser.getAttributeName(i),
							parser.getAttributeValue(i));
				}
			}

			currentXPath.addElement(currentElement);

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
	public XPath getCurrentXPath() {
		return currentXPath;
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
