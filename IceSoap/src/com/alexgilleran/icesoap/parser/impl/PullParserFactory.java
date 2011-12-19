/**
 * 
 */
package com.alexgilleran.icesoap.parser.impl;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

/**
 * Gets new instances of {@link XmlPullParser} and {@link XmlSerializer} using
 * the {@link XmlPullParserFactory} that works on and off an actual Android
 * phone, rather than the Android {@link Xml} helper package which fails to work
 * in unit tests, even when using Robolectric.
 * 
 * @author Alex Gilleran
 */
public class PullParserFactory {
	/** The singleton instance of this class */
	private static PullParserFactory INSTANCE = null;
	/** The XmlPullParserFactory to use for new parsers and serializers */
	private XmlPullParserFactory parserFactory = getFactory();

	/**
	 * Private singleton constructor.
	 */
	private PullParserFactory() {

	}

	/**
	 * Lazily loads an instance of {@link XmlPullParserFactory}
	 * 
	 * @return An instance
	 */
	private XmlPullParserFactory getFactory() {
		try {
			return XmlPullParserFactory.newInstance();
		} catch (XmlPullParserException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Gets the singleton instance of the factory.
	 * 
	 * @return An instance of the factory.
	 */
	public static PullParserFactory getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PullParserFactory();
		}

		return INSTANCE;
	}

	/**
	 * Builds a new instance of {@link XmlPullParser}
	 * 
	 * @return a new instance of {@link XmlPullParser}
	 */
	public XmlPullParser buildParser() {
		try {
			return parserFactory.newPullParser();
		} catch (XmlPullParserException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Builds a new instance of {@link XmlSerializer}
	 * 
	 * @return a new instance of {@link XmlSerializer}
	 */
	public XmlSerializer buildSerializer() {
		try {
			return parserFactory.newSerializer();
		} catch (XmlPullParserException e) {
			throw new RuntimeException(e);
		}
	}
}
