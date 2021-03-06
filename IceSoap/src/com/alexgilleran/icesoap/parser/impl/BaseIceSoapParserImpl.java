package com.alexgilleran.icesoap.parser.impl;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;
import com.alexgilleran.icesoap.exception.ClassDefException;
import com.alexgilleran.icesoap.exception.XMLParsingException;
import com.alexgilleran.icesoap.exception.XPathParsingException;
import com.alexgilleran.icesoap.parser.IceSoapParser;
import com.alexgilleran.icesoap.parser.XPathPullParser;
import com.alexgilleran.icesoap.request.Request;
import com.alexgilleran.icesoap.xpath.XPathFactory;
import com.alexgilleran.icesoap.xpath.XPathRepository;
import com.alexgilleran.icesoap.xpath.elements.XPathElement;

/**
 * Contains common code for the implementations of {@link IceSoapParser}.
 * 
 * @author Alex Gilleran
 * 
 * @param <ReturnType>
 *            The type to build with this parser.
 */
public abstract class BaseIceSoapParserImpl<ReturnType> implements IceSoapParser<ReturnType> {
	/**
	 * The xpath(s) of the XML node that this parser will parse within - it will
	 * start parsing at the start of these nodes, and stop parsing at the end.
	 */
	private XPathRepository<XPathElement> rootXPaths;

	/**
	 * Instantiates a new {@link BaseIceSoapParserImpl}
	 * 
	 * @param rootXPath
	 *            The (single) root xpath of the type to parse - the parser will
	 *            keep traversing the document until it finds this xpath, then
	 *            keep parsing until it reaches the end of the xpath.
	 */
	protected BaseIceSoapParserImpl(XPathElement rootXPath) {
		this(new XPathRepository<XPathElement>(rootXPath, rootXPath));
	}

	/**
	 * Instantiates a new {@link BaseIceSoapParserImpl}.
	 * 
	 * @param rootXPaths
	 *            An XPathRepository containing the root xpath(s) of the type to
	 *            parse - the parser will keep traversing the document until it
	 *            finds any of the xpaths in the repository, then keep parsing
	 *            until it reaches the end of the xpath. Note that this is an
	 *            "OR" relationship intended to reflect the functionality of the
	 *            XPath "|" - it will only parse an object corresponding to the
	 *            first one of these XPaths it encounters, then finish.
	 */
	protected BaseIceSoapParserImpl(XPathRepository<XPathElement> rootXPaths) {
		this.rootXPaths = rootXPaths;

		checkIfXPathsRelative(rootXPaths);
	}

	/**
	 * Checks the supplied XPaths to ensure that none are relative - if they
	 * are, throws a {@link ClassDefException}.
	 * 
	 * @param xpathRepo
	 *            The {@link XPathRepository} to check.
	 */
	private void checkIfXPathsRelative(XPathRepository<XPathElement> xpathRepo) {
		for (XPathElement xpath : xpathRepo.keySet()) {
			if (xpath.isRelative()) {
				throw new ClassDefException("Attempted to use " + this.getClass().getSimpleName()
						+ " to parse relative XPath " + xpath.toString() + ". Please either annotate this class with "
						+ XMLObject.class.getSimpleName()
						+ " and an absolute XPath, or make sure to only use it as a field in other "
						+ XMLObject.class.getSimpleName() + "-annotated classes rather than passing it directly to a "
						+ Request.class.getSimpleName() + " or " + IceSoapParser.class.getSimpleName() + " object");
			}
		}
	}

	/**
	 * Gets the root xpaths being parsed.
	 * 
	 * @return The root xpaths.
	 */
	protected XPathRepository<XPathElement> getRootXPaths() {
		return rootXPaths;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ReturnType parse(InputStream inputStream) throws XMLParsingException {
		XPathPullParserImpl parser = new XPathPullParserImpl();
		try {
			parser.setInput(inputStream, null);
		} catch (XmlPullParserException e) {
			throw new XMLParsingException(e);
		}

		return parse(parser);
	}

	/**
	 * Uses the supplied {@link XPathPullParser} to parse new instance of
	 * ReturnType.
	 * 
	 * This method allows for nested parsers... i.e. a parser parses a big
	 * object, instantiating new parsers to parse the non-primitive fields
	 * within. The current location of the parser passed in can be the very
	 * start of the document, or at some place in the middle.
	 * 
	 * @param parser
	 *            The {@link XPathPullParser} instance to use for parsing.
	 * @return A parsed instance of ReturnType
	 * @throws XMLParsingException
	 */
	protected final ReturnType parse(XPathPullParser parser) throws XMLParsingException {
		return parse(parser, null);
	}

	/**
	 * See: {@link #parse(XPathPullParser)}
	 * 
	 * This allows an existing object to be passed in so it can be modified by
	 * the method.
	 * 
	 * @param parser
	 *            The {@link XPathPullParser} instance to use for parsing.
	 * @param objectToModify
	 *            The object instance to be parsed - this will have its fields
	 *            modified by the parser according to the xml.
	 * @return A parsed instance of ReturnType.
	 * @throws XMLParsingException
	 *             If a problem is encountered with the underlying
	 *             {@link XmlPullParser} - usually as a result of poorly-formed
	 *             XML.
	 * 
	 */
	protected final ReturnType parse(XPathPullParser parser, ReturnType objectToModify) throws XMLParsingException {
		boolean isInRootElement = false;

		try {
			while (true) {
				if (rootXPaths == null) {
					// No root xpath is specified - just parse every element
					// that comes along.
					objectToModify = parseElement(parser, objectToModify);
				} else {
					if (isInRootElement == false && enteringRootElement(parser)) {
						isInRootElement = true;
					} else if (isInRootElement == true && exitingRootElement(parser)) {
						isInRootElement = false;

						// No need to keep parsing with this parser
						break;
					}

					if (isInRootElement && isEventTypeParseable(parser.getEventType())) {
						objectToModify = parseElement(parser, objectToModify);
					}
				}

				// If we're at the end of the document, break out, otherwise
				// keep parsing.
				if (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
					parser.next();
				} else {
					break;
				}
			}

			// At this point we're either at the end of the root element or the
			// document, so the object should be completely parsed - return it.
			return objectToModify;
		} catch (XmlPullParserException e) {
			throw new XMLParsingException(e);
		}
	}

	/**
	 * Determines whether the {@link XPathPullParser} event provided should
	 * result in an attempt to parse the event (i.e. a new tag, text or
	 * attribute event).
	 * 
	 * @param eventType
	 *            The event, as an {@link XPathPullParser} event integer.
	 * @return Whether this event is parseable.
	 */
	private boolean isEventTypeParseable(int eventType) {
		return eventType != XPathPullParser.END_TAG && eventType != XPathPullParser.END_DOCUMENT;
	}

	/**
	 * Determines whether the current location of the provided parser is at the
	 * very start of a root XPath.
	 * 
	 * @param parser
	 *            The parser to determine the location.
	 * @return Whether the location is at the start of a root XPath.
	 * @throws XmlPullParserException
	 *             If a problem is encountered in the {@link XmlPullParser}
	 */
	private boolean enteringRootElement(XPathPullParser parser) throws XmlPullParserException {
		return parser.getEventType() == XPathPullParser.START_TAG && rootXPaths.contains(parser.getCurrentElement());
	}

	/**
	 * Determines whether the current location of the provided parser is at the
	 * very end of a root XPath.
	 * 
	 * @param parser
	 *            The parser to determine the location.
	 * @return Whether the location is at the end of a root XPath.
	 * @throws XmlPullParserException
	 *             If a problem is encountered in the {@link XmlPullParser}
	 */
	private boolean exitingRootElement(XPathPullParser parser) throws XmlPullParserException {
		return parser.getEventType() == XPathPullParser.END_TAG && rootXPaths.contains(parser.getCurrentElement());
	}

	/**
	 * Initializes the parsed object if an instance is not passed in.
	 * 
	 * @return A new, blank instance of ReturnType.
	 */
	protected abstract ReturnType initializeParsedObject();

	/**
	 * Uses the extending class to parse the element at the
	 * {@link XPathPullParser}'s current position.
	 * 
	 * @param pullParser
	 *            An instance of {@link XPathPullParser}.
	 * @param objectToModify
	 *            The object to modify - can be left null, in which case it will
	 *            be initialized with {@link #initializeParsedObject()}.
	 * @return A parsed instance of ReturnType.
	 * @throws XMLParsingException
	 *             If there's a problem with parsing e.g. invalid xml.
	 * @throws XmlPullParserException
	 */
	private ReturnType parseElement(XPathPullParser pullParser, ReturnType objectToModify) throws XMLParsingException,
			XmlPullParserException {
		if (objectToModify == null) {
			objectToModify = initializeParsedObject();
		}

		switch (pullParser.getEventType()) {
		case XPathPullParser.START_TAG:
			objectToModify = onNewTag(pullParser, objectToModify);
			break;
		case XPathPullParser.TEXT:
		case XPathPullParser.ATTRIBUTE:
			objectToModify = onText(pullParser, objectToModify);
			break;
		}

		return objectToModify;
	}

	/**
	 * Called every time a new XML tag is encountered.
	 * 
	 * In this method, it is an implementing class' responsibility to get the
	 * details it needs from the passed {@link XPathPullParser} and either
	 * modify the passed objectToModify with these details, or do nothing if the
	 * event wasn't relevant.
	 * 
	 * @param pullParser
	 *            The {@link XPathPullParser} to get details from.
	 * @param objectToModify
	 *            The object to modify.
	 * @return Should be the passed in objectToModify object, with changes.
	 * @throws XMLParsingException
	 *             Thrown if problems occur during the XML parse, usually due to
	 *             badly formed XML.
	 */
	protected abstract ReturnType onNewTag(XPathPullParser pullParser, ReturnType objectToModify)
			throws XMLParsingException;

	/**
	 * Called every time a text or attribute field is discovered - use
	 * {@link XPathPullParser#getCurrentValue()} to get the text value itself.
	 * 
	 * When this method is called, the implementing class should get the
	 * necessary information from the passed {@link XPathPullParser}, and use it
	 * to modify the passed objectToModify value before returning it.
	 * 
	 * @param pullParser
	 *            The pull parser that's encountered the text event
	 * @param objectToModify
	 *            The object to modify.
	 * @return Should be the passed in objectToModify object, with changes.
	 * @throws XMLParsingException
	 *             Thrown if problems occur during the XML parse, usually due to
	 *             badly formed XML.
	 */
	protected abstract ReturnType onText(XPathPullParser pullParser, ReturnType objectToModify)
			throws XMLParsingException;

	/**
	 * Gets the generic type of the contents of a list type... e.g. when passed
	 * the type of a list that is List<String>, this will return String.
	 * 
	 * @param typeToParse
	 *            The GENERIC type of the list - if calling this with an
	 *            argument from a {@link Field}, ensure you use
	 *            {@link Field#getGenericType()}
	 * @return The class of the list items.
	 */
	protected Class<?> getListItemClass(Type typeToParse) {
		ParameterizedType paramType = (ParameterizedType) typeToParse;
		Type listItemType = paramType.getActualTypeArguments()[0];

		Class<?> listItemClass = (Class<?>) listItemType;
		return listItemClass;
	}

	/**
	 * Retrieves the root xpath(s) from the annotation on the class - there will
	 * be only one in most cases, but more if the xpath "|" operator has been
	 * used to define multiple xpaths. Note that this may return null.
	 * 
	 * @param targetClass
	 *            Class to retrieve from
	 * @return A repo containing all the possible root XPaths for the class.
	 */
	protected static XPathRepository<XPathElement> retrieveRootXPaths(Class<?> targetClass) {
		XMLObject xPathAnnot = getXMLObjectAnnot(targetClass);

		if (xPathAnnot != null) {
			return compileXPath(xPathAnnot, targetClass);
		} else {
			throw new ClassDefException("Class " + targetClass.getName() + " to be created with "
					+ BaseIceSoapParserImpl.class.getSimpleName() + " was not annotated with the "
					+ XMLObject.class.getSimpleName() + " annotation - please add this annotation");
		}
	}

	/**
	 * Gets the @{@link XMLObject} annotation from a class hierarchy. Checks the
	 * passed class, if the annotation isn't present it will check the
	 * superclass and so on until it either finds the annotation and returns it,
	 * or gets to the top of the hierarchy and returns null.
	 * 
	 * @param targetClass
	 *            The class to look at.
	 * @return The XMLObject annotation if found, otherwise null.
	 */
	private static XMLObject getXMLObjectAnnot(Class<?> targetClass) {
		while (!targetClass.equals(Object.class)) {
			XMLObject annotation = targetClass.getAnnotation(XMLObject.class);

			if (annotation != null) {
				return annotation;
			}

			targetClass = targetClass.getSuperclass();
		}

		return null;
	}

	/**
	 * Gets the xpath from a {@link XMLField} annotation and compiles it to a
	 * repo of {@link XPathElement}s.
	 * 
	 * @param soapFieldAnnot
	 *            The annotation to get the xpath from .
	 * @param sourceField
	 *            The field that the annotation is sourced from (used for
	 *            exception messages).
	 * @return A repo of XPathElements.
	 */
	protected final static XPathRepository<XPathElement> compileXPath(XMLField soapFieldAnnot, Field sourceField) {
		return compileXPath(soapFieldAnnot.value(), sourceField.toString());
	}

	/**
	 * Gets the xpath from a {@link XMLObject} annotation and compiles it to a
	 * repository of xpath elements.
	 * 
	 * @param soapObjectAnnot
	 *            The SOAPObject annotation instance to get the xpath from.
	 * @param sourceClass
	 *            The class that the annotation comes from.
	 * @return A repo of XPathElements
	 * @see #compileXPath(String, String)
	 */
	protected final static XPathRepository<XPathElement> compileXPath(XMLObject soapObjectAnnot, Class<?> sourceClass) {
		if (soapObjectAnnot.value() != "") {
			return compileXPath(soapObjectAnnot.value(), sourceClass.getSimpleName());
		}

		// Root xpath is not mandatory
		return null;
	}

	/**
	 * Uses the {@link XPathFactory} to compile a String-based XPath to a
	 * repository of IceSoap {@link XPathElement}.
	 * 
	 * @param xpathString
	 *            The string to parse.
	 * @param source
	 *            The source of the xpath - this is used to throw an exception
	 *            message if the parsing hits a problem - for a class this
	 *            should be the class name, for a field this should be the field
	 *            class and name ({@link Field#toString()}).
	 * @return A repo of XPathElements
	 */
	private static XPathRepository<XPathElement> compileXPath(String xpathString, String source) {
		try {
			return XPathFactory.getInstance().compile(xpathString);
		} catch (XPathParsingException e) {
			throw new ClassDefException("The xpath expression " + xpathString + " specified for " + source
					+ " was an invalid XPath expression", e);
		}
	}

}
