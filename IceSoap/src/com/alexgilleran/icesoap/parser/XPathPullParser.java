/**
 * 
 */
package com.alexgilleran.icesoap.parser;

import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.alexgilleran.icesoap.exception.XMLParsingException;
import com.alexgilleran.icesoap.xpath.elements.XPathElement;

/**
 * Wrapper around {@link XmlPullParser} that keeps track of the current element
 * in XPath form.
 * 
 * Only a few methods of {@link XmlPullParser} are supported. Note that unlike
 * the standard {@link XmlPullParser}, this treats attributes as an event in the
 * same sense as a node.
 * 
 * @author Alex Gilleran
 * 
 */
public interface XPathPullParser {
	/** Indicates that the current event is the start of a document. */
	int START_DOCUMENT = XmlPullParser.START_DOCUMENT;
	/** Indicates that the current event is the end of a document. */
	int END_DOCUMENT = XmlPullParser.END_DOCUMENT;
	/** Indicates that the current event is the start of a tag. */
	int START_TAG = XmlPullParser.START_TAG;
	/** Indicates that the current event is the end of a tag. */
	int END_TAG = XmlPullParser.END_TAG;
	/** Indicates that the current event is a text value. */
	int TEXT = XmlPullParser.TEXT;
	/** Indicates that the current event is an attribute value. */
	int ATTRIBUTE = 5;

	/**
	 * Get the {@link String} value of the current node, whether attribute or
	 * text. For a text node, this will move to the end of the text node after
	 * getting the value.
	 * 
	 * Note that if the event is {@link #ATTRIBUTE}, this will only ever return
	 * the <i>value</i> of the attribute - get the name with
	 * {@link #getCurrentElement()}.
	 * 
	 * @return The string value of the current node.
	 */
	String getCurrentValue();

	/**
	 * Delegates to {@link XmlPullParser#next()} - gets the next event from the
	 * pull parser.
	 * 
	 * Note that although this is much the same as {@link XmlPullParser#next()},
	 * this treats attribute values as events in their own right, rather than
	 * just tags. Attribute events will return the value {@link #ATTRIBUTE}.
	 * 
	 * @return The type of the next event, as an int.
	 * @throws XMLParsingException
	 *             If an error is encountered in the XML.
	 * @see org.xmlpull.v1.XmlPullParser#next()
	 */
	int next() throws XMLParsingException;

	/**
	 * Gets the current element being passed, as an {@link XPathElement}.
	 * 
	 * @return the current XPath
	 */
	XPathElement getCurrentElement();

	/**
	 * Gets the type of the current event. Be aware that although this delegates
	 * to {@link XmlPullParser#getEventType()} in most cases, on attribute
	 * values it will return {@link #ATTRIBUTE}, which is specific to this
	 * interface.
	 * 
	 * @return The current event type as an int - compare with the public
	 *         constants in this class.
	 * @throws XmlPullParserException
	 *             If an error is encountered in the XML.
	 */
	int getEventType() throws XmlPullParserException;

	/**
	 * Sets the input for this parser.
	 * 
	 * @param inputStream
	 *            The input stream to set.
	 * @param inputEncoding
	 *            The encoding of the stream - null will attempt auto-detection.
	 * @throws XmlPullParserException
	 *             If an error is encountered in the XML.
	 * @see org.xmlpull.v1.XmlPullParser#setInput(java.io.InputStream,
	 *      java.lang.String)
	 */
	void setInput(InputStream inputStream, String inputEncoding) throws XmlPullParserException;

	/**
	 * Checks if the current element has the xsi:nil attribute.
	 * 
	 * @return Whether it does.
	 */
	boolean isCurrentValueXsiNil();

}