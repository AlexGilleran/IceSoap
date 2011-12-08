package com.alexgilleran.icesoap.parser;

import java.io.IOException;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.alexgilleran.icesoap.observer.ObserverRegistry;
import com.alexgilleran.icesoap.observer.SOAPObserver;

/**
 * Base class for parsers. The base parser provides two methods that must be
 * overridden:
 * 
 * - getParsedObject is called whenever an element with the root element name
 * passed into this object in the constructor is encountered - this method must
 * then parse the object desired using the XMLPullParser provided.
 * 
 * 
 * Parsers are intended to call other instances of parsers - for instance, if
 * you had an XML document like this:
 * 
 * <manufacturer> ... <make> ... <model>...</model> <model>...</model> </make>
 * </manufacturer>
 * 
 * You could have a parser to return a list of model objects, called by a parser
 * that returns a list of make objects, that is in turn called by a parser that
 * returns a single manufacturer object.
 * 
 * @author Alex Gilleran
 * 
 * @param <T>
 *            The class of the object to return. For instance, if I wanted to
 *            return a "Product" object from this parser, I would specify
 *            <Product> and override the resulting methods
 */
public abstract class BaseParser<T> implements Parser<T> {
	protected String rootElementName;
	private ObserverRegistry<T> registry = new ObserverRegistry<T>();
	private String[][] attributes;
	private List<String> elements;

	/**
	 * Initialises the parser
	 * 
	 * @param parser
	 *            The XMLPullParser instance to use - passing this in allows the
	 *            parser to be called from other parsers
	 * @param rootElementName
	 *            The root element for this object within the XML - parseTag
	 *            will be called whenever this element is encountered
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	public BaseParser(String rootElementName) {
		this.rootElementName = rootElementName;
	}

	public BaseParser(String rootElementName, String[]... rootElementAttributes) {
		this.rootElementName = rootElementName;
		this.attributes = rootElementAttributes;
	}

	public String getRootElementName() {
		return rootElementName;
	}

	/**
	 * Parses an object by looping through every child tag, calling parseTag()
	 * on each START_TAG event. Stops at the end of the parent tag and returns
	 * the object that has been parsed.
	 * 
	 * @return The object created by parsing the tag
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	@Override
	public T parse(XPathXmlPullParser parser, T objectToModify)
			throws XmlPullParserException, IOException {
		T objectSoFar;

		if (objectToModify == null) {
			objectSoFar = initializeParsedObject();
		} else {
			objectSoFar = objectToModify;
		}

		if (moveToRootElementStart(parser)) {
			// While not the end of the tag with this name
			while (shouldKeepGoing(parser)) {
				// Call parsetag on each START_TAG event
				if (parser.getEventType() == XPathXmlPullParser.START_TAG) {
					String name = parser.getName();
					objectSoFar = parseTag(name, parser, objectSoFar);
				}

				parser.next();

			}

			notifyListeners(objectSoFar);
		} else {
			objectSoFar = null;
		}

		return objectSoFar;
	}

	@Override
	public T parse(XPathXmlPullParser parser) throws XmlPullParserException,
			IOException {
		return parse(parser, initializeParsedObject());
	}

	private boolean shouldKeepGoing(XPathXmlPullParser parser)
			throws XmlPullParserException {
		return (!(parser.getEventType() == XmlPullParser.END_TAG && parser
				.getName().equalsIgnoreCase(rootElementName)) && !(parser
				.getEventType() == XmlPullParser.END_DOCUMENT));
	}

	private boolean isAtRootElementStart(XPathXmlPullParser parser)
			throws XmlPullParserException {
		if (parser.getEventType() != XmlPullParser.START_TAG) {
			return false;
		}

		if (!parser.getName().equals(getRootElementName())) {
			return false;
		}

		if (attributes != null) {
			for (String[] attribute : attributes) {
				if (!parser.getAttributeValue(attribute[0], attribute[1])
						.equals(attribute[2])) {
					return false;
				}
			}
		}

		return true;
	}

	private boolean moveToRootElementStart(XPathXmlPullParser parser)
			throws XmlPullParserException, IOException {
		int startDepth = parser.getDepth();

		while (!isAtRootElementStart(parser)) {
			if (!shouldKeepGoing(parser) || parser.getDepth() < startDepth) {
				return false;
			}

			parser.next();
		}

		return true;
	}

	/**
	 * Initialises a new instance of a parsed object - called at the start of
	 * parseChildren().
	 * 
	 * Use this to initialise the object that will be passed into parseTag()
	 */
	public abstract T initializeParsedObject();

	/**
	 * Called every time a new START_TAG event is encountered by the
	 * XPathXmlPullParser.
	 * 
	 * Generally you'll want to put some kind of mechanism (if/else if or
	 * equivalent) in this class to determine which tag has been started, then
	 * use the methods of the XMLPullParser to parse it. If the tag contains a
	 * list of children, you might want to create another parser object to
	 * handle it.
	 * 
	 * @param tagName
	 *            The name of the tag encountered
	 * @param parser
	 *            The XPathXmlPullParser being used - it will be at the start of
	 *            the tag described by tagName
	 * @param parsedObject
	 *            The parsed object so far
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	protected abstract T parseTag(String tagName, XPathXmlPullParser parser,
			T parsedObject) throws XmlPullParserException, IOException;

	public void addListener(SOAPObserver<T> listener) {
		registry.addListener(listener);
	}

	public void removeListener(SOAPObserver<T> listener) {
		registry.removeListener(listener);
	}

	private void notifyListeners(T item) {
		registry.notifyListeners(item);
	}
}
