package com.alexgilleran.icesoap.parser.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.management.modelmbean.XMLParseException;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;
import com.alexgilleran.icesoap.exception.ClassDefException;
import com.alexgilleran.icesoap.exception.XMLParsingException;
import com.alexgilleran.icesoap.parser.IceSoapListParser;
import com.alexgilleran.icesoap.parser.IceSoapParser;
import com.alexgilleran.icesoap.parser.XPathPullParser;
import com.alexgilleran.icesoap.xpath.XPathRepository;
import com.alexgilleran.icesoap.xpath.elements.XPathElement;

/**
 * Implementation of {@link IceSoapParser} for parsing an individual object.
 * 
 * This takes in a class, then uses the {@link XMLObject} and {@link XMLField}
 * annotations on it to determine the xpath of this object in SOAP calls, and
 * the xpaths of each field within the object.
 * 
 * Once it has these, every time it gets a call from
 * {@link BaseIceSoapParserImpl#onNewTag(XPathPullParser, Object)}, it looks up
 * the current xpath in its repository of {@link XPathElement}s to see if it has
 * a field that matches it - if it does, it looks at the type of the field.
 * 
 * <li>For basic types (primitives, {@link String}, {@link BigDecimal}, as
 * defined by {@link #TEXT_NODE_CLASSES}), it gets the value from the parser and
 * sets that field to the value - if the value is not an XML Text field an
 * exception is thrown.</li> <li>For complex types annotated by
 * {@link XMLObject}, it will instantiate another parser to parse an instance of
 * this object, then set the instance to that field and continue parsing.</li>
 * <li>If the field is a {@link List}, it will instantiate an
 * {@link IceSoapListParser} and use it to parse the list... when the parser is
 * finished, it will set the field to the returned list and continue parsing.</li>
 * 
 * @author Alex Gilleran
 * 
 * @param <ReturnType>
 *            The type of the object being parsed.
 */
public class IceSoapParserImpl<ReturnType> extends
		BaseIceSoapParserImpl<ReturnType> {
	/**
	 * An {@link XPathRepository} that maps xpaths to the fields represented by
	 * them.
	 */
	private XPathRepository<Field> fieldXPaths;

	/**
	 * The class of ReturnType.
	 */
	private Class<ReturnType> targetClass;

	/**
	 * Types that can be parsed by simply getting the text value from a node or
	 * attribute.
	 */
	@SuppressWarnings("unchecked")
	private static final Set<Class<?>> TEXT_NODE_CLASSES = new HashSet<Class<?>>(
			Arrays.asList(long.class, float.class, int.class, double.class,
					BigDecimal.class, String.class, Date.class));

	/**
	 * Instantiates a new parser.
	 * 
	 * @param targetClass
	 *            The class of the object to parse - note that this must have a
	 *            zero-arg constructor
	 */
	public IceSoapParserImpl(Class<ReturnType> targetClass) {
		this(targetClass, retrieveRootXPath(targetClass));
	}

	/**
	 * Instantiates a new parser. * @param targetClass The class of the object
	 * to parse.
	 * 
	 * @param targetClass
	 *            The class of the object to parse - note that this must have a
	 *            zero-arg constructor
	 * @param rootXPath
	 *            The root XPath to parse within - the parser will traverse the
	 *            XML document until it finds the rootXPath and keep parsing
	 *            until it finds the end, then finish. Note that the xml node
	 *            described by this {@link XPathElement} can be outside the node
	 *            specified by the {@link XMLObject} field of targetClass or the
	 *            same, but cannot be within it.
	 */
	public IceSoapParserImpl(Class<ReturnType> targetClass,
			XPathElement rootXPath) {
		super(rootXPath);
		this.targetClass = targetClass;

		fieldXPaths = getFieldXPaths(targetClass);
	}

	/**
	 * Gets the xpaths declared with the {@link XMLField} annotation on a class.
	 * 
	 * @param targetClass
	 *            The class to get xpaths for.
	 * @return An {@link XPathRepository} linking xpaths to fields.
	 */
	private XPathRepository<Field> getFieldXPaths(Class<ReturnType> targetClass) {
		XPathRepository<Field> fieldXPaths = new XPathRepository<Field>();

		Class<?> currentClass = targetClass;

		while (!currentClass.equals(Object.class)) {
			addXPathFieldsToRepo(currentClass, fieldXPaths);
			currentClass = currentClass.getSuperclass();
		}

		return fieldXPaths;
	}

	/**
	 * Adds the fields from the specified class to the passed
	 * {@link XPathRepository}, with the XPaths specified in the
	 * {@link XMLField} annotations.
	 * 
	 * @param targetClass
	 *            The class to draw fields from
	 * @param fieldXPaths
	 *            The repository to add fields too
	 */
	private void addXPathFieldsToRepo(Class<?> targetClass,
			XPathRepository<Field> fieldXPaths) {
		for (Field field : targetClass.getDeclaredFields()) {
			XMLField xPath = field.getAnnotation(XMLField.class);

			if (xPath != null) {
				// Annotation is present - get the XPath from it
				XPathElement lastFieldElement;

				if (!xPath.value().equals(XMLField.BLANK_XPATH_STRING)) {
					// If the XPath has a value specified, compile it
					lastFieldElement = compileXPath(xPath, field);
					XPathElement firstFieldElement = lastFieldElement
							.getFirstElement();

					if (firstFieldElement.isRelative()) {
						// If the element is relative, add it to the absolute
						// XPath
						// of its enclosing object.
						firstFieldElement.setPreviousElement(getRootXPath());
					}
				} else {
					// XPath has no value - set to the root value
					lastFieldElement = getRootXPath();
				}

				fieldXPaths.put(lastFieldElement, field);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Uses {@link Class#newInstance()} to instantiate a new instance of
	 * ReturnType.
	 */
	@Override
	public ReturnType initializeParsedObject() {
		try {
			return targetClass.newInstance();
		} catch (InstantiationException e) {
			throwInitializationException(e);
		} catch (IllegalAccessException e) {
			throwInitializationException(e);
		}

		return null;
	}

	/**
	 * Throws a {@link ClassDefException} encountered when initializing the
	 * class.
	 * 
	 * @param cause
	 *            The root exception.
	 */
	private void throwInitializationException(Throwable cause) {
		throw new ClassDefException(
				"An exception was encountered while trying to instantiate a new instance of "
						+ targetClass.getName()
						+ ". This is probably because it doesn't implement a zero-arg constructor. To fix this, either change it so it has a zero-arg constructor, extend "
						+ getClass().getSimpleName()
						+ " and override the initializeParsedObject method, or make sure to always pass an existing object to the parser.",
				cause);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * This method works by getting the current xpath from the
	 * {@link XPathPullParser} and seeing if there are any fields that match it.
	 * If there aren't, it does nothing. If there are, it checks the type of the
	 * field - if it's a type that can be set from a text node value, it gets
	 * the value from the node and sets it to the field. If it's a complex
	 * field, it calls {@link #getParserForClass(Type, Class, XPathPullParser)}
	 * to parse it.
	 * 
	 * @throws
	 */
	@Override
	protected ReturnType onNewTag(XPathPullParser xmlPullParser,
			ReturnType objectToModify) throws XMLParsingException {
		Field fieldToSet = fieldXPaths.get(xmlPullParser.getCurrentElement());

		if (fieldToSet != null) {
			if (!TEXT_NODE_CLASSES.contains(fieldToSet.getType())) {
				Type fieldType = fieldToSet.getGenericType();
				Object valueToSet = getParserForClass(fieldType,
						fieldToSet.getType(), xmlPullParser).parse(
						xmlPullParser);
				setField(objectToModify, fieldToSet, valueToSet);

			}
		}

		return objectToModify;
	}

	@Override
	protected ReturnType onText(XPathPullParser pullParser,
			ReturnType objectToModify) throws XMLParsingException {
		Field fieldToSet = fieldXPaths.get(pullParser.getCurrentElement());

		if (fieldToSet != null) {
			if (TEXT_NODE_CLASSES.contains(fieldToSet.getType())) {
				Object valueToSet = convertToFieldType(fieldToSet,
						pullParser.getCurrentValue());
				setField(objectToModify, fieldToSet, valueToSet);

			}
		}

		return objectToModify;
	}

	/**
	 * Given a class, attempts to find the appropriate parser for the class. If
	 * the class is an implementation of {@link List}, it attempts to get the
	 * class that the item is a list of, instantiate a {@link IceSoapParser} to
	 * parse that class, then creates a {@link IceSoapListParser}to wrap it and
	 * returns the {@link IceSoapListParser}
	 * 
	 * @param <E>
	 *            The type of the object to create a parser for
	 * @param typeToParse
	 *            The type to parse (as a {@link Type}
	 * @param classToParse
	 *            The class to parse (as a {@link Class} - this should be the
	 *            same as typeToParse.
	 * @param pullParser
	 *            The pull parser used to do the parsing.
	 * @return A new instance of {@link IceSoapParser}
	 */
	@SuppressWarnings("unchecked")
	private <E> BaseIceSoapParserImpl<?> getParserForClass(Type typeToParse,
			Class<E> classToParse, XPathPullParser pullParser) {
		if (List.class.isAssignableFrom(classToParse)) {
			// Class to parse is a list - find out the parameterized type of the
			// list and create a parser for that, then wrap a ListParser around
			// it.

			ParameterizedType paramType = (ParameterizedType) typeToParse;
			Type listItemType = paramType.getActualTypeArguments()[0];

			Class<?> listItemClass = (Class<?>) listItemType;

			BaseIceSoapParserImpl<?> itemParser = new IceSoapParserImpl(
					listItemClass);

			return new IceSoapListParserImpl(listItemClass,
					pullParser.getCurrentElement(), itemParser);
		} else {
			// The type is not a list - create a parser
			return new IceSoapParserImpl<E>(classToParse,
					pullParser.getCurrentElement());
		}
	}

	/**
	 * Sets the supplied {@link Field} in the supplied object to the supplied
	 * value, handling setting of accessibility and reflection exceptions.
	 * 
	 * @param objectToModify
	 *            The object to set the value on
	 * @param fieldToSet
	 *            The field (as a {@link Field} to set
	 * @param valueToSet
	 *            The value to set to the field.
	 */
	private void setField(ReturnType objectToModify, Field fieldToSet,
			Object valueToSet) {
		try {
			boolean isAccessibleBefore = fieldToSet.isAccessible();
			fieldToSet.setAccessible(true);
			fieldToSet.set(objectToModify, valueToSet);
			fieldToSet.setAccessible(isAccessibleBefore);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Accepts a {@link String} taken from an XML parser and converts it into a
	 * primitive or primitive-esque (e.g. {@link BigDecimal}) type with the
	 * correct method.
	 * 
	 * @param field
	 *            The field to get the appropriate type from.
	 * @param valueString
	 *            The string to parse to the correct type.
	 * @return The string's value as the appropriate type.
	 * @throws XMLParseException
	 */
	private Object convertToFieldType(Field field, String valueString)
			throws XMLParsingException {
		if (int.class.isAssignableFrom(field.getType())) {
			return Integer.parseInt(valueString);
		} else if (long.class.isAssignableFrom(field.getType())) {
			return Long.parseLong(valueString);
		} else if (float.class.isAssignableFrom(field.getType())) {
			return Float.parseFloat(valueString);
		} else if (double.class.isAssignableFrom(field.getType())) {
			return Double.parseDouble(valueString);
		} else if (boolean.class.isAssignableFrom(field.getType())) {
			return Boolean.parseBoolean(valueString);
		} else if (BigDecimal.class.isAssignableFrom(field.getType())) {
			return new BigDecimal(valueString);
		} else if (Date.class.isAssignableFrom(field.getType())) {
			try {
				return new SimpleDateFormat(field.getAnnotation(XMLField.class)
						.dateFormat()).parse(valueString);
			} catch (ParseException e) {
				throw new XMLParsingException(
						"Encountered date parsing exception when parsing "
								+ field.toString()
								+ " with format "
								+ field.getAnnotation(XMLField.class)
										.dateFormat() + " for value "
								+ valueString, e);
			}
		} else {
			return valueString;
		}
	}
}
