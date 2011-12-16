/**
 * 
 */
package com.alexgilleran.icesoap.parser.impl;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/**
 * @author Alex Gilleran
 * 
 */
public class PullParserFactory {
	private static PullParserFactory INSTANCE = null;

	private XmlPullParserFactory parserFactory = getFactory();

	private PullParserFactory() {

	}

	private XmlPullParserFactory getFactory() {
		try {
			return XmlPullParserFactory.newInstance();
		} catch (XmlPullParserException e) {
			throw new RuntimeException(e);
		}
	}

	public static PullParserFactory getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PullParserFactory();
		}

		return INSTANCE;
	}

	public XmlPullParser buildParser() {
		try {
			return parserFactory.newPullParser();
		} catch (XmlPullParserException e) {
			throw new RuntimeException(e);
		}
	}
}
