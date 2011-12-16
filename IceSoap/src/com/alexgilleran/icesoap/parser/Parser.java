package com.alexgilleran.icesoap.parser;

import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;

import com.alexgilleran.icesoap.exception.XmlParsingException;

/**
 * Interface for all parsers. Note that this parser is not the same concept as
 * the {@link XmlPullParser} - rather, implementations of this class wrap around
 * the {@link XmlPullParser} and use it to parse an instance of the ReturnType
 * object.
 * 
 * @author Alex Gilleran
 * 
 * @param <ReturnType>
 *            The class of the object to return. For instance, if I wanted to
 *            return a "Product" object from this parser, I would specify {code
 *            <Product>} and override the resulting methods
 */
public interface Parser<ReturnType> {
	/**
	 * Instantiates a ReturnType object and then uses the provided
	 * {@link XPathPullParser} to populate it with data.
	 * 
	 * 
	 * @return The object created by parsing the tag
	 * @throws XmlParsingException
	 */
	ReturnType parse(InputStream inputStream) throws XmlParsingException;
}
