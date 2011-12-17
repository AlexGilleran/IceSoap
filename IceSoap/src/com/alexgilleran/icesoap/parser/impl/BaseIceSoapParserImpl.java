package com.alexgilleran.icesoap.parser.impl;

import java.io.InputStream;
import java.lang.reflect.Field;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.alexgilleran.icesoap.annotation.SOAPField;
import com.alexgilleran.icesoap.annotation.SOAPObject;
import com.alexgilleran.icesoap.exception.ClassDefException;
import com.alexgilleran.icesoap.exception.XPathParsingException;
import com.alexgilleran.icesoap.exception.XmlParsingException;
import com.alexgilleran.icesoap.parser.IceSoapParser;
import com.alexgilleran.icesoap.parser.XPathPullParser;
import com.alexgilleran.icesoap.xpath.XPathFactory;
import com.alexgilleran.icesoap.xpath.elements.XPathElement;

/**
 * Contains common code for the implementation of {@link IceSoapParser}
 * 
 * @author Alex Gilleran
 * 
 * @param <T>
 */
public abstract class BaseIceSoapParserImpl<T> implements IceSoapParser<T> {
	/**
	 * The xpath of the XML node that this parser will parse within - it will
	 * start parsing at the start of this node, and stop parsing at the end.
	 */
	private XPathElement rootXPath;

	protected BaseIceSoapParserImpl(XPathElement rootXPath) {
		this.rootXPath = rootXPath;
	}

	protected XPathElement getRootXPath() {
		return rootXPath;
	}

	@Override
	public T parse(InputStream inputStream) throws XmlParsingException {
		XPathPullParserImpl parser = new XPathPullParserImpl();
		try {
			parser.setInput(inputStream, null);
		} catch (XmlPullParserException e) {
			throw new XmlParsingException(e);
		}

		return parse(parser);
	}

	protected final T parse(XPathPullParser parser) throws XmlParsingException {
		return parse(parser, null);
	}

	protected final T parse(XPathPullParser parser, T objectToModify)
			throws XmlParsingException {
		boolean isInRootElement = false;

		try {
			while (true) {
				if (rootXPath == null) {
					objectToModify = parseElement(parser, objectToModify);
				} else {
					if (isInRootElement == false
							&& parser.getEventType() == XmlPullParser.START_TAG
							&& rootXPath.matches(parser.getCurrentElement())) {
						isInRootElement = true;
					} else if (isInRootElement == true
							&& parser.getEventType() == XmlPullParser.END_TAG
							&& rootXPath.matches(parser.getCurrentElement())) {
						isInRootElement = false;
						break;
					}

					if ((parser.getEventType() == XPathPullParser.START_TAG || parser
							.getEventType() == XPathPullParser.ATTRIBUTE)
							&& isInRootElement) {
						objectToModify = parseElement(parser, objectToModify);
					}
				}

				if (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
					parser.next();
				} else {
					break;
				}
			}

			return objectToModify;
		} catch (XmlPullParserException e) {
			throw new XmlParsingException(e);
		}
	}

	protected abstract T initializeParsedObject();

	private T parseElement(XPathPullParser xmlPullParser, T objectToModify)
			throws XmlParsingException {
		if (objectToModify == null) {
			objectToModify = initializeParsedObject();
		}

		objectToModify = onNewTag(xmlPullParser, objectToModify);

		return objectToModify;
	}

	protected abstract T onNewTag(XPathPullParser xmlPullParser,
			T objectToModify) throws XmlParsingException;

	/**
	 * Retrieves the root xpath from the annotation on the class. Note that this
	 * may return null.
	 * 
	 * @param targetClass
	 *            Class to retrieve from
	 * @return Root xpath or null if none is specified
	 */
	protected static XPathElement retrieveRootXPath(Class<?> targetClass) {
		SOAPObject xPathAnnot = targetClass.getAnnotation(SOAPObject.class);

		if (xPathAnnot != null) {
			return compileXPath(xPathAnnot, targetClass);
		} else {
			throw new ClassDefException(
					"Class "
							+ targetClass.getName()
							+ " to be created with AnnotationParser - it was not annotated with the "
							+ SOAPField.class.getSimpleName()
							+ " annotation - please add this annotation");
		}
	}

	protected static XPathElement compileXPath(SOAPField xPathAnnot,
			Field sourceField) {
		if (xPathAnnot.value() != null) {
			return compileXPath(xPathAnnot.value(), sourceField.toString());
		} else {
			throw new ClassDefException(
					"The "
							+ SOAPField.class.getSimpleName()
							+ " annotation on field "
							+ sourceField.toString()
							+ " did not specify a mandatory XPath expression for a value.");
		}
	}

	protected static XPathElement compileXPath(SOAPObject xPathAnnot,
			Class<?> sourceClass) {
		if (xPathAnnot.value() != null) {
			return compileXPath(xPathAnnot.value(), sourceClass.getSimpleName());
		}

		// Root xpath is not mandatory except for list parser.
		return null;
	}

	private static XPathElement compileXPath(String xPathString, String source) {
		try {
			return XPathFactory.getInstance().compile(xPathString);
		} catch (XPathParsingException e) {
			throw new ClassDefException("The xpath expression " + xPathString
					+ " specified for " + source
					+ " was an invalid XPath expression");
		}
	}

}
