package com.alexgilleran.icesoap.parser;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.xmlpull.v1.XmlPullParserException;

import com.alexgilleran.icesoap.annotation.SOAPField;
import com.alexgilleran.icesoap.exception.ClassDefException;
import com.alexgilleran.icesoap.xpath.XPath;
import com.alexgilleran.icesoap.xpath.XPathRepository;

public class AnnotationParser<T> extends BaseAnnotationParser<T> {
	private XPathRepository<Field> fieldXPaths;
	private Class<T> targetClass;
	private Set<Class<?>> textNodeClasses = new HashSet<Class<?>>(
			Arrays.asList(long.class, float.class, int.class, double.class,
					BigDecimal.class, String.class));

	public AnnotationParser(Class<T> targetClass) {
		super(targetClass);

		this.targetClass = targetClass;
		fieldXPaths = getFieldXPaths(targetClass);
	}

	public AnnotationParser(Class<T> targetClass, XPath rootXPath) {
		super(rootXPath);

		this.targetClass = targetClass;
		fieldXPaths = getFieldXPaths(targetClass);
	}

	private XPathRepository<Field> getFieldXPaths(Class<T> targetClass) {
		XPathRepository<Field> fieldXPaths = new XPathRepository<Field>();

		for (Field field : targetClass.getDeclaredFields()) {
			SOAPField xPath = field.getAnnotation(SOAPField.class);

			if (xPath != null) {
				fieldXPaths.put(new XPath(compileXPath(xPath, field)).getLastElement(), field);
			}
		}

		return fieldXPaths;
	}

	public T initializeParsedObject() {
		try {
			return targetClass.newInstance();
		} catch (InstantiationException e) {
			throwInitializationException(e);
		} catch (IllegalAccessException e) {
			throwInitializationException(e);
		}

		return null;
	}

	private void throwInitializationException(Throwable e) {
		throw new ClassDefException(
				"An exception was encountered while trying to instantiate a new instance of "
						+ targetClass.getName()
						+ ". This is probably because it doesn't implement a zero-arg constructor. To fix this, either change it so it has a zero-arg constructor, extend "
						+ this.getClass().getSimpleName()
						+ " and override the initializeParsedObject method, or make sure to always pass an existing object to the parser.",
				e);
	}

	@Override
	protected T onNewTag(XPathXmlPullParser xmlPullParser, T objectToModify)
			throws XmlPullParserException, IOException {
		Field fieldToSet = fieldXPaths.get(xmlPullParser.getCurrentXPath().getLastElement());

		if (fieldToSet != null) {
			if (textNodeClasses.contains(fieldToSet.getType())) {
				setField(
						objectToModify,
						fieldToSet,
						convertToFieldType(fieldToSet,
								xmlPullParser.getCurrentValue()));

			} else {
				Type fieldType = fieldToSet.getGenericType();
				setField(
						objectToModify,
						fieldToSet,
						getParserForClass(fieldType, fieldToSet.getType(),
								xmlPullParser).parse(xmlPullParser));

			}
		}

		return objectToModify;
	}

	private <E> Parser<?> getParserForClass(Type typeToParse,
			Class<E> classToParse, XPathXmlPullParser xmlPullParser) {
		// TODO: Caching these will make things a bunch quicker. Probably.

		if (List.class.isAssignableFrom(classToParse)) {
			ParameterizedType paramType = (ParameterizedType) typeToParse;
			Type listItemType = paramType.getActualTypeArguments()[0];

			// if (!(typeToParse instanceof Class)) {
			// // TODO: Unchecked Exc. with good message here
			// return null;
			// }

			Class<?> listItemClass = (Class<?>) listItemType;

			Parser<?> itemParser = getParserForClass(listItemType,
					listItemClass, xmlPullParser);

			return new AnnotationListParser(listItemClass,
					xmlPullParser.getCurrentXPath(), itemParser);
		} else {
			return new AnnotationParser<E>(classToParse,
					retrieveRootXPath(classToParse));
		}

	}

	private void setField(T objectToModify, Field fieldToSet, Object valueToSet) {
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

	private Object convertToFieldType(Field field, String valueString) {
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
		} else {
			return valueString;
		}
	}
}
