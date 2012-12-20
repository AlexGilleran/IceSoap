package com.alexgilleran.icesoap.parser.impl;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.alexgilleran.icesoap.exception.XMLParsingException;
import com.alexgilleran.icesoap.parser.XPathPullParser;
import com.alexgilleran.icesoap.xml.XMLNode;
import com.alexgilleran.icesoap.xpath.elements.XPathElement;
import com.alexgilleran.icesoap.xpath.elements.impl.AttributeXPathElement;
import com.alexgilleran.icesoap.xpath.elements.impl.SingleSlashXPathElement;

/**
 * Wrapper for {@link XmlPullParser} for XPath operations. Basically this acts
 * in much the same manner as an {@link XmlPullParser}, but keeps track of the
 * current position in the document as an {@link XPathElement}, which can be
 * retrieved at any time. It also changes the parser so that attributes are an
 * event.
 * 
 * @author Alex Gilleran
 * 
 */
public class XPathPullParserImpl implements XPathPullParser {
	/** The wrapped {@link XmlPullParser}. */
	private XmlPullParser parser = PullParserFactory.getInstance().buildParser();
	/** The element that the parser is currently at. */
	private XPathElement currentElement;
	/** The type of the current event as an int. */
	private int eventType;
	/**
	 * Flag - keeps track of whether to remove the last XPath element on the
	 * next event. This is necessary so that when {@link #getCurrentElement()}
	 * is called during an END_TAG event, it will return the element that is
	 * ending rather than the one below it in the hierarchy.
	 */
	private boolean removeLastXPathElement;

	/** Index of the current attribute being parsed, within the current tag */
	private int currentAttributeIndex = 0;

	public XPathPullParserImpl() {
		try {
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
		} catch (XmlPullParserException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCurrentValue() {
		if (currentElement.isAttribute()) {
			return getCurrentAttributeValue();
		} else {
			return parser.getText();
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
	public int next() throws XMLParsingException {
		try {
			// Trim any existing attributes
			trimAttribute();

			if (moreAttributesToParse()) {
				eventType = nextAttribute();
			} else {
				eventType = parser.next();
				updateXPath();
			}

			return eventType;
		} catch (XmlPullParserException e) {
			throw new XMLParsingException(e);
		} catch (IOException e) {
			throw new XMLParsingException(e);
		}
	}

	/**
	 * Advances the parser to the next attribute.
	 * 
	 * @return The event type as an int.
	 */
	private int nextAttribute() {
		// There are attributes here - process them in turn before we
		// get to the value
		currentElement = new AttributeXPathElement(new SingleSlashXPathElement(
				parser.getAttributeName(currentAttributeIndex), currentElement));

		currentAttributeIndex++;

		return XPathPullParser.ATTRIBUTE;
	}

	/**
	 * Updates the current XPath and associated state variables
	 * 
	 * @throws XmlPullParserException
	 */
	private void updateXPath() throws XmlPullParserException {
		trimEndedElement();

		switch (getEventType()) {
		case XmlPullParser.START_TAG:
			addNewElement();
			break;
		case XmlPullParser.END_TAG:
			flagLastElementForRemoval();
			break;
		}
	}

	/**
	 * Flags the last element to be removed on the next event.
	 */
	private void flagLastElementForRemoval() {
		removeLastXPathElement = true;
	}

	/**
	 * Adds a the element that the parser is currently at to the current xpath,
	 * trimming any attribute information.
	 */
	private void addNewElement() {
		// As we've started a new element, the attribute index starts from again
		currentAttributeIndex = 0;

		currentElement = new SingleSlashXPathElement(parser.getName(), currentElement);

		// Add predicates
		addPredicates();
	}

	/**
	 * Adds the attributes of the current xml node to the current xpath as
	 * predicates.
	 */
	private void addPredicates() {
		int attributeCount = parser.getAttributeCount();
		if (attributeCount > 0) {
			for (int i = 0; i < attributeCount; i++) {
				currentElement.addPredicate(parser.getAttributeName(i), parser.getAttributeValue(i));
			}
		}
	}

	@Override
	public boolean isCurrentValueXsiNil() {
		return (XMLNode.XSI_NIL_TRUE.equals(parser.getAttributeValue(XMLNode.NS_URI_XSI, XMLNode.XSI_NIL_NAME)));
	}

	/**
	 * Trims attribute data from the current xpath, and resets the attribute
	 * index to 0.
	 */
	private void trimAttribute() {
		if (currentElement != null && currentElement.isAttribute()) {
			currentElement = currentElement.getPreviousElement();
		}
	}

	/**
	 * If the last element has been flagged for removal, remove it.
	 */
	private void trimEndedElement() {
		if (removeLastXPathElement) {
			// If the flag was set to remove the last XPath element, do it now
			currentElement = currentElement.getPreviousElement();
			removeLastXPathElement = false;
		}
	}

	/**
	 * Determines whether attributes remain to parse as events.
	 * 
	 * @return true if attributes remain to parse as events, otherwise false
	 * @throws XmlPullParserException
	 */
	private boolean moreAttributesToParse() throws XmlPullParserException {
		return parser.getEventType() == XmlPullParser.START_TAG
				&& currentAttributeIndex <= parser.getAttributeCount() - 1;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public XPathElement getCurrentElement() {
		return currentElement;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getEventType() throws XmlPullParserException {
		return eventType;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setInput(InputStream inputStream, String inputEncoding) throws XmlPullParserException {
		parser.setInput(inputStream, inputEncoding);
	}
}
