/**
 * 
 */
package com.alexgilleran.icesoap.parser;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.alexgilleran.icesoap.exception.XmlParsingException;
import com.alexgilleran.icesoap.xpath.elements.XPathElement;

/**
 * @author Alex Gilleran
 * 
 */
public interface XPathPullParser {
	public static int START_DOCUMENT = XmlPullParser.START_DOCUMENT;
	public static final int END_DOCUMENT = XmlPullParser.END_DOCUMENT;
	public static final int START_TAG = XmlPullParser.START_TAG;
	public static final int END_TAG = XmlPullParser.END_TAG;
	public static final int TEXT = XmlPullParser.TEXT;
	public static final int ATTRIBUTE = 5;

	/**
	 * 
	 * Get the String value of the current node, whether attribute or text.
	 * 
	 * @return The string value of the current node.
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public abstract String getCurrentValue() throws XmlParsingException;

	/**
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 * @see org.xmlpull.v1.XmlPullParser#next()
	 */
	public abstract int next() throws XmlParsingException;

	/**
	 * @return the currentXPath
	 */
	public abstract XPathElement getCurrentElement();

	/**
	 * @return
	 * @throws XmlPullParserException
	 * @see org.xmlpull.v1.XmlPullParser#getEventType()
	 */
	public abstract int getEventType() throws XmlPullParserException;

	/**
	 * @param arg0
	 * @param arg1
	 * @throws XmlPullParserException
	 * @see org.xmlpull.v1.XmlPullParser#setInput(java.io.InputStream,
	 *      java.lang.String)
	 */
	public abstract void setInput(InputStream arg0, String arg1)
			throws XmlPullParserException;

}