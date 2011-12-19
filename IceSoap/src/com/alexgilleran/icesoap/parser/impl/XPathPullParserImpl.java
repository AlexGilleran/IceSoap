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
 * Wrapper for {@link XmlPullParser} for XPath operations. Basically this acts
 * in much the same manner as an {@link XmlPullParser}, but keeps track of the
 * current position in the document as an {@link XPathElement}, which can be
 * retrieved at any time.
 * 
 * @author Alex Gilleran
 * 
 */
public class XPathPullParserImpl implements XPathPullParser {
	/** The wrapped {@link XmlPullParser} */
	private XmlPullParser parser = PullParserFactory.getInstance()
			.buildParser();
	/** The element that the parser is currently at */
	private XPathElement currentElement;
	/**
	 * Flag - keeps track of whether to remove the last XPath element on the
	 * next event. This is necessary so that when {@link #getCurrentElement()}
	 * is called during an END_TAG event, it will return the element that is
	 * ending rather than the one below it in the hierarchy.
	 */
	private boolean removeLastXPathElement;

	/** Index of the current attribute being parsed, within the current tag */
	private int currentAttributeIndex = 0;

	/**
	 * {@inheritDoc}
	 */
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
	 * Gets the current attribute value.
	 * 
	 * @return The value of the attribute currently pointed at - if there is no
	 *         current attribute, null.
	 */
	private String getCurrentAttributeValue() {
		if ((parser.getAttributeCount() - 1) >= (currentAttributeIndex - 1)) {
			return parser.getAttributeValue(currentAttributeIndex - 1);
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int next() throws XmlParsingException {
		int next;

		try {
			if (parser.getEventType() == XmlPullParser.START_TAG
					&& currentAttributeIndex <= parser.getAttributeCount() - 1) {
				// There are attributes here - process them in turn before we
				// get to the value
				next = XPathPullParser.ATTRIBUTE;

				currentElement = new AttributeXPathElement(
						parser.getAttributeName(currentAttributeIndex),
						currentElement);

				currentAttributeIndex++;
			} else {
				// No attributes (or no more attributes)
				next = parser.next();

				// If we were parsing an attribute, we'll want to go back a step
				// to get the tag that the attribute was on as we haven't
				// returned that yet
				if (currentElement.isAttribute()) {
					currentElement = currentElement.getPreviousElement();
				}

				// Reset the attribute index
				currentAttributeIndex = 0;
			}

			if (removeLastXPathElement) {
				// If the flag was set to remove the last XPath element, do it
				// now
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
		if (currentAttributeIndex == 0) {
			return parser.getEventType();
		} else {
			return XPathPullParser.ATTRIBUTE;
		}
	}

	@Override
	public void setInput(InputStream inputStream, String inputEncoding)
			throws XmlPullParserException {
		parser.setInput(inputStream, inputEncoding);
	}

}
