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
public class XPathXmlPullParser implements XmlPullParser {
	private XmlPullParser parser = Xml.newPullParser();
	private XPath currentXPath = new XPath();

	public XPathXmlPullParser() {

	}

	/**
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 * @see org.xmlpull.v1.XmlPullParser#next()
	 */
	public int next() throws XmlPullParserException, IOException {
		int result = parser.next();

		updateXPath();

		return result;
	}

	private void updateXPath() throws XmlPullParserException {
		switch (parser.getEventType()) {
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
			currentXPath.removeElement();
			break;
		}
	}

	/**
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 * @see org.xmlpull.v1.XmlPullParser#nextText()
	 */
	public String nextText() throws XmlPullParserException, IOException {
		String result = parser.nextText();

		updateXPath();

		return result;
	}

	/**
	 * @return the currentXPath
	 */
	public XPath getCurrentXPath() {
		return currentXPath;
	}

	/**
	 * @return
	 * @see org.xmlpull.v1.XmlPullParser#getDepth()
	 */
	public int getDepth() {
		return parser.getDepth();
	}

	/**
	 * @return
	 * @throws XmlPullParserException
	 * @see org.xmlpull.v1.XmlPullParser#getEventType()
	 */
	public int getEventType() throws XmlPullParserException {
		return parser.getEventType();
	}

	/**
	 * @return
	 * @see org.xmlpull.v1.XmlPullParser#getName()
	 */
	public String getName() {
		return parser.getName();
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

	/**
	 * @param arg0
	 * @param arg1
	 * @throws XmlPullParserException
	 * @see org.xmlpull.v1.XmlPullParser#defineEntityReplacementText(java.lang.String,
	 *      java.lang.String)
	 */
	public void defineEntityReplacementText(String arg0, String arg1)
			throws XmlPullParserException {
		parser.defineEntityReplacementText(arg0, arg1);
	}

	/**
	 * @return
	 * @see org.xmlpull.v1.XmlPullParser#getAttributeCount()
	 */
	public int getAttributeCount() {
		return parser.getAttributeCount();
	}

	/**
	 * @param arg0
	 * @return
	 * @see org.xmlpull.v1.XmlPullParser#getAttributeName(int)
	 */
	public String getAttributeName(int arg0) {
		return parser.getAttributeName(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 * @see org.xmlpull.v1.XmlPullParser#getAttributeNamespace(int)
	 */
	public String getAttributeNamespace(int arg0) {
		return parser.getAttributeNamespace(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 * @see org.xmlpull.v1.XmlPullParser#getAttributePrefix(int)
	 */
	public String getAttributePrefix(int arg0) {
		return parser.getAttributePrefix(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 * @see org.xmlpull.v1.XmlPullParser#getAttributeType(int)
	 */
	public String getAttributeType(int arg0) {
		return parser.getAttributeType(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 * @see org.xmlpull.v1.XmlPullParser#getAttributeValue(int)
	 */
	public String getAttributeValue(int arg0) {
		return parser.getAttributeValue(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 * @see org.xmlpull.v1.XmlPullParser#getAttributeValue(java.lang.String,
	 *      java.lang.String)
	 */
	public String getAttributeValue(String arg0, String arg1) {
		return parser.getAttributeValue(arg0, arg1);
	}

	/**
	 * @return
	 * @see org.xmlpull.v1.XmlPullParser#getColumnNumber()
	 */
	public int getColumnNumber() {
		return parser.getColumnNumber();
	}

	/**
	 * @param arg0
	 * @return
	 * @see org.xmlpull.v1.XmlPullParser#getFeature(java.lang.String)
	 */
	public boolean getFeature(String arg0) {
		return parser.getFeature(arg0);
	}

	/**
	 * @return
	 * @see org.xmlpull.v1.XmlPullParser#getInputEncoding()
	 */
	public String getInputEncoding() {
		return parser.getInputEncoding();
	}

	/**
	 * @return
	 * @see org.xmlpull.v1.XmlPullParser#getLineNumber()
	 */
	public int getLineNumber() {
		return parser.getLineNumber();
	}

	/**
	 * @return
	 * @see org.xmlpull.v1.XmlPullParser#getNamespace()
	 */
	public String getNamespace() {
		return parser.getNamespace();
	}

	/**
	 * @param arg0
	 * @return
	 * @see org.xmlpull.v1.XmlPullParser#getNamespace(java.lang.String)
	 */
	public String getNamespace(String arg0) {
		return parser.getNamespace(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 * @throws XmlPullParserException
	 * @see org.xmlpull.v1.XmlPullParser#getNamespaceCount(int)
	 */
	public int getNamespaceCount(int arg0) throws XmlPullParserException {
		return parser.getNamespaceCount(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 * @throws XmlPullParserException
	 * @see org.xmlpull.v1.XmlPullParser#getNamespacePrefix(int)
	 */
	public String getNamespacePrefix(int arg0) throws XmlPullParserException {
		return parser.getNamespacePrefix(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 * @throws XmlPullParserException
	 * @see org.xmlpull.v1.XmlPullParser#getNamespaceUri(int)
	 */
	public String getNamespaceUri(int arg0) throws XmlPullParserException {
		return parser.getNamespaceUri(arg0);
	}

	/**
	 * @return
	 * @see org.xmlpull.v1.XmlPullParser#getPositionDescription()
	 */
	public String getPositionDescription() {
		return parser.getPositionDescription();
	}

	/**
	 * @return
	 * @see org.xmlpull.v1.XmlPullParser#getPrefix()
	 */
	public String getPrefix() {
		return parser.getPrefix();
	}

	/**
	 * @param arg0
	 * @return
	 * @see org.xmlpull.v1.XmlPullParser#getProperty(java.lang.String)
	 */
	public Object getProperty(String arg0) {
		return parser.getProperty(arg0);
	}

	/**
	 * @return
	 * @see org.xmlpull.v1.XmlPullParser#getText()
	 */
	public String getText() {
		return parser.getText();
	}

	/**
	 * @param arg0
	 * @return
	 * @see org.xmlpull.v1.XmlPullParser#getTextCharacters(int[])
	 */
	public char[] getTextCharacters(int[] arg0) {
		return parser.getTextCharacters(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 * @see org.xmlpull.v1.XmlPullParser#isAttributeDefault(int)
	 */
	public boolean isAttributeDefault(int arg0) {
		return parser.isAttributeDefault(arg0);
	}

	/**
	 * @return
	 * @throws XmlPullParserException
	 * @see org.xmlpull.v1.XmlPullParser#isEmptyElementTag()
	 */
	public boolean isEmptyElementTag() throws XmlPullParserException {
		return parser.isEmptyElementTag();
	}

	/**
	 * @return
	 * @throws XmlPullParserException
	 * @see org.xmlpull.v1.XmlPullParser#isWhitespace()
	 */
	public boolean isWhitespace() throws XmlPullParserException {
		return parser.isWhitespace();
	}

	/**
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 * @see org.xmlpull.v1.XmlPullParser#nextTag()
	 */
	public int nextTag() throws XmlPullParserException, IOException {
		return parser.nextTag();
	}

	/**
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 * @see org.xmlpull.v1.XmlPullParser#nextToken()
	 */
	public int nextToken() throws XmlPullParserException, IOException {
		return parser.nextToken();
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @throws XmlPullParserException
	 * @throws IOException
	 * @see org.xmlpull.v1.XmlPullParser#require(int, java.lang.String,
	 *      java.lang.String)
	 */
	public void require(int arg0, String arg1, String arg2)
			throws XmlPullParserException, IOException {
		parser.require(arg0, arg1, arg2);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @throws XmlPullParserException
	 * @see org.xmlpull.v1.XmlPullParser#setFeature(java.lang.String, boolean)
	 */
	public void setFeature(String arg0, boolean arg1)
			throws XmlPullParserException {
		parser.setFeature(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @throws XmlPullParserException
	 * @see org.xmlpull.v1.XmlPullParser#setInput(java.io.Reader)
	 */
	public void setInput(Reader arg0) throws XmlPullParserException {
		parser.setInput(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @throws XmlPullParserException
	 * @see org.xmlpull.v1.XmlPullParser#setProperty(java.lang.String,
	 *      java.lang.Object)
	 */
	public void setProperty(String arg0, Object arg1)
			throws XmlPullParserException {
		parser.setProperty(arg0, arg1);
	}

}
