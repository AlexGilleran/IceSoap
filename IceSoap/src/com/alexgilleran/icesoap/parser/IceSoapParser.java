package com.alexgilleran.icesoap.parser;

import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;

import com.alexgilleran.icesoap.exception.XMLParsingException;

/**
 * Interface for all parsers. Note that this parser is not the same concept as
 * the {@link XmlPullParser} - rather, implementations of this class wrap around
 * the {@link XmlPullParser} and use it to parse an instance of the ReturnType
 * object.
 * 
 * @author Alex Gilleran
 * 
 * @param <ReturnType>
 *            The class of the object to return.
 */
public interface IceSoapParser<ReturnType> {
	/**
	 * Instantiates a ReturnType object and then populates it with data parsed
	 * from the provided {@link InputStream}.
	 * 
	 * @param inputStream
	 *            A stream containing the XML to parse.
	 * @return The object created by parsing the tag
	 * @throws XMLParsingException
	 *             In the event of invalid XML being encountered during the
	 *             parse.
	 */
	ReturnType parse(InputStream inputStream) throws XMLParsingException;
}
