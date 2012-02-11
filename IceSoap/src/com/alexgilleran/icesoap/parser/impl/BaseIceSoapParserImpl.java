package com.alexgilleran.icesoap.parser.impl;

import java.io.InputStream;
import java.lang.reflect.Field;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;
import com.alexgilleran.icesoap.exception.ClassDefException;
import com.alexgilleran.icesoap.exception.XPathParsingException;
import com.alexgilleran.icesoap.exception.XMLParsingException;
import com.alexgilleran.icesoap.parser.IceSoapParser;
import com.alexgilleran.icesoap.parser.XPathPullParser;
import com.alexgilleran.icesoap.request.Request;
import com.alexgilleran.icesoap.xpath.XPathFactory;
import com.alexgilleran.icesoap.xpath.elements.XPathElement;

/**
 * Contains common code for the implementation of {@link IceSoapParser}
 * 
 * @author Alex Gilleran
 * 
 * @param <ReturnType>
 *            The type to build with this parser.
 */
public abstract class BaseIceSoapParserImpl<ReturnType> implements
		IceSoapParser<ReturnType> {
	/**
	 * The xpath of the XML node that this parser will parse within - it will
	 * start parsing at the start of this node, and stop parsing at the end.
	 */
	private XPathElement rootXPath;

	/**
	 * Instantiates a new {@link BaseIceSoapParserImpl}
	 * 
	 * @param rootXPath
	 *            The root xpath of the type to parse - the parser will keep
	 *            traversing the document until it finds this xpath, then keep
	 *            parsing until it reaches the end of the xpath.
	 */
	protected BaseIceSoapParserImpl(XPathElement rootXPath) {
		this.rootXPath = rootXPath;

		if (rootXPath.isRelative()) {
			throw new ClassDefException(
					"Attempted to use "
							+ this.getClass().getSimpleName()
							+ " to parse relative XPath "
							+ rootXPath.toString()
							+ ". Please either annotate this class with "
							+ XMLObject.class.getSimpleName()
							+ " and an absolute XPath, or make sure to only use it as a field in other "
							+ XMLObject.class.getSimpleName()
							+ "-annotated classes rather than passing it directly to a "
							+ Request.class.getSimpleName() + " or "
							+ IceSoapParser.class.getSimpleName() + " object");
		}
	}

	/**
	 * Gets the root xpath being parsed
	 * 
	 * @return The root xpath.
	 */
	protected XPathElement getRootXPath() {
		return rootXPath;
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
	 * object, calling parsers to parse the non-primitive fields within. The
	 * parser passed in can be at the very start of the document, or at some
	 * place in the middle.
	 * 
	 * @param parser
	 *            The {@link XPathPullParser} instance to use for parsing.
	 * @return A parsed instance of ReturnType
	 * @throws XMLParsingException
	 */
	protected final ReturnType parse(XPathPullParser parser)
			throws XMLParsingException {
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
	 * 
	 */
	protected final ReturnType parse(XPathPullParser parser,
			ReturnType objectToModify) throws XMLParsingException {
		boolean isInRootElement = false;

		try {
			while (true) {
				if (rootXPath == null) {
					// No root xpath is specified - just parse every element
					// that comes along.
					objectToModify = parseElement(parser, objectToModify);
				} else {
					// If this matches the root element, it must be either
					// entering or exiting it. If it's entering (START_TAG), set
					// the isInRootElement flag to true, if exiting set to
					// false.
					if (isInRootElement == false
							&& parser.getEventType() == XPathPullParser.START_TAG
							&& rootXPath.matches(parser.getCurrentElement())) {
						isInRootElement = true;
					} else if (isInRootElement == true
							&& parser.getEventType() == XPathPullParser.END_TAG
							&& rootXPath.matches(parser.getCurrentElement())) {
						isInRootElement = false;

						// No need to keep parsing with this parser - break out
						// of the loop.
						break;
					}

					if ((parser.getEventType() == XPathPullParser.START_TAG || parser
							.getEventType() == XPathPullParser.ATTRIBUTE)
							&& isInRootElement) {
						// If we're starting a tag and in the root element,
						// parse this element.
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
	 * Initializes the parsed object if an instance is not passed in. In general
	 * this is done through reflection.
	 * 
	 * @return A new, blank instance of ReturnType.
	 */
	protected abstract ReturnType initializeParsedObject();

	/**
	 * Uses the extending class to parse the element at the
	 * {@link XPathPullParser}'s current position.
	 * 
	 * @param pullParser
	 *            An instance of {@link XPathPullParser}
	 * @param objectToModify
	 *            The object to modify - can be left null, in which case it will
	 *            be initialized with {@link #initializeParsedObject()}.
	 * @return A parsed instance of ReturnType.
	 * @throws XMLParsingException
	 *             If there's a problem with parsing e.g. invalid xml.
	 */
	private ReturnType parseElement(XPathPullParser pullParser,
			ReturnType objectToModify) throws XMLParsingException {
		if (objectToModify == null) {
			objectToModify = initializeParsedObject();
		}

		objectToModify = onNewParsingEvent(pullParser, objectToModify);

		return objectToModify;
	}

	/**
	 * Called every time a new parsing event occurs - on a new tag or a new
	 * attribute value.
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
	 */
	protected abstract ReturnType onNewParsingEvent(XPathPullParser pullParser,
			ReturnType objectToModify) throws XMLParsingException;

	/**
	 * Retrieves the root xpath from the annotation on the class. Note that this
	 * may return null.
	 * 
	 * @param targetClass
	 *            Class to retrieve from
	 * @return Root xpath or null if none is specified
	 */
	protected static XPathElement retrieveRootXPath(Class<?> targetClass) {
		XMLObject xPathAnnot = getXMLObjectAnnot(targetClass);

		if (xPathAnnot != null) {
			return compileXPath(xPathAnnot, targetClass);
		} else {
			throw new ClassDefException("Class " + targetClass.getName()
					+ " to be created with "
					+ BaseIceSoapParserImpl.class.getSimpleName()
					+ " was not annotated with the "
					+ XMLObject.class.getSimpleName()
					+ " annotation - please add this annotation");
		}
	}

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
	 * Gets the xpath from a {@link XMLField} annotation and compiles it to an
	 * xpath element.
	 * 
	 * @param soapFieldAnnot
	 *            The annotation to get the xpath from .
	 * @param sourceField
	 *            The field that the annotation is sourced from (used for
	 *            exception messages).
	 * @return The last element of the compiled xpath.
	 */
	protected final static XPathElement compileXPath(XMLField soapFieldAnnot,
			Field sourceField) {
		if (soapFieldAnnot.value() != null) {
			return compileXPath(soapFieldAnnot.value(), sourceField.toString());
		} else {
			throw new ClassDefException(
					"The "
							+ XMLField.class.getSimpleName()
							+ " annotation on field "
							+ sourceField.toString()
							+ " did not specify a mandatory XPath expression for a value.");
		}
	}

	/**
	 * Gets the xpath from a {@link XMLObject} annotation and compiles it to an
	 * xpath element.
	 * 
	 * @param soapObjectAnnot
	 *            The SOAPObject annotation instance to get the xpath from.
	 * @param sourceClass
	 *            The class that the annotation comes from.
	 * @return The last element of the compiled xpath.
	 * @see #compileXPath(String, String)
	 */
	protected final static XPathElement compileXPath(XMLObject soapObjectAnnot,
			Class<?> sourceClass) {
		if (soapObjectAnnot.value() != "") {
			return compileXPath(soapObjectAnnot.value(),
					sourceClass.getSimpleName());
		}

		// Root xpath is not mandatory
		return null;
	}

	/**
	 * Uses the {@link XPathFactory} to compile a String-based XPath to an
	 * IceSoap {@link XPathElement}.
	 * 
	 * @param xpathString
	 *            The string to parse.
	 * @param source
	 *            The source of the xpath - this is used to throw an exception
	 *            message if the parsing hits a problem - for a class this
	 *            should be the class name, for a field this should be the field
	 *            class and name ({@link Field#toString()}).
	 * @return The the last element in the xpath.
	 */
	private static XPathElement compileXPath(String xpathString, String source) {
		try {
			return XPathFactory.getInstance().compile(xpathString);
		} catch (XPathParsingException e) {
			throw new ClassDefException("The xpath expression " + xpathString
					+ " specified for " + source
					+ " was an invalid XPath expression");
		}
	}

}
