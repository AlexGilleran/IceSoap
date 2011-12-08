package com.alexgilleran.icesoap.parser;

import java.io.IOException;

import org.jaxen.saxpath.SAXPathException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.alexgilleran.icesoap.annotation.FindByXPath;
import com.alexgilleran.icesoap.annotation.RootXPath;
import com.alexgilleran.icesoap.exception.ClassDefException;
import com.alexgilleran.icesoap.observer.ObserverRegistry;
import com.alexgilleran.icesoap.observer.SOAPObserver;
import com.alexgilleran.icesoap.xpath.XPath;
import com.alexgilleran.icesoap.xpath.XPathFactory;

public abstract class BaseAnnotationParser<T> implements Parser<T> {
	private XPath rootXPath;
	private ObserverRegistry<T> registry = new ObserverRegistry<T>();

	protected BaseAnnotationParser(Class<?> targetClass) {
		rootXPath = retrieveRootXPath(targetClass);
	}

	protected BaseAnnotationParser(XPath rootXPath) {
		this.rootXPath = rootXPath;
	}

	protected XPath getRootXPath() {
		return rootXPath;
	}

	@Override
	public void addListener(SOAPObserver<T> listener) {
		registry.addListener(listener);
	}

	@Override
	public void removeListener(SOAPObserver<T> listener) {
		registry.removeListener(listener);
	}

	@Override
	public final T parse(XPathXmlPullParser parser)
			throws XmlPullParserException, IOException {
		return parse(parser, null);
	}

	@Override
	public final T parse(XPathXmlPullParser parser, T objectToModify)
			throws XmlPullParserException, IOException {
		boolean isInRootElement = false;

		while (true) {
			if (rootXPath == null) {
				objectToModify = parseElement(parser, objectToModify);
			} else {
				if (isInRootElement == false
						&& parser.getEventType() == XmlPullParser.START_TAG
						&& rootXPath.matches(parser.getCurrentXPath())) {
					isInRootElement = true;
				} else if (isInRootElement == true
						&& parser.getEventType() == XmlPullParser.END_TAG
						&& rootXPath.matches(parser.getCurrentXPath())) {
					isInRootElement = false;
				}

				if (parser.getEventType() == XmlPullParser.START_TAG
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

		if (objectToModify != null) {
			registry.notifyListeners(objectToModify);
		}

		return objectToModify;
	}

	private T parseElement(XPathXmlPullParser xmlPullParser, T objectToModify)
			throws XmlPullParserException, IOException {
		if (objectToModify == null) {
			objectToModify = initializeParsedObject();
		}

		objectToModify = onNewTag(xmlPullParser, objectToModify);

		return objectToModify;
	}

	protected abstract T onNewTag(XPathXmlPullParser xmlPullParser,
			T objectToModify) throws XmlPullParserException, IOException;

	/**
	 * Retrieves the root xpath from the annotation on the task. Note that this
	 * may return null.
	 * 
	 * @param targetClass
	 *            Class to retrieve from
	 * @return Root xpath or null if none is specified
	 */
	private XPath retrieveRootXPath(Class<?> targetClass) {
		RootXPath xPathAnnot = targetClass.getAnnotation(RootXPath.class);

		if (xPathAnnot != null) {
			return compileXPath(xPathAnnot);
		} else {
			throw new ClassDefException("Class to create with "
					+ this.getClass().getSimpleName()
					+ " was not annotated with the "
					+ FindByXPath.class.getSimpleName()
					+ " annotation - please add this annotation");
		}
	}

	protected XPath compileXPath(FindByXPath xPathAnnot) {
		if (xPathAnnot.value() != null) {
			return compileXPath(xPathAnnot.value());
		} else {
			throw new ClassDefException(
					"The "
							+ FindByXPath.class.getSimpleName()
							+ " annotation on class "
							+ this.getClass().getSimpleName()
							+ " did not specify a mandatory XPath expression for a value.");
		}
	}

	protected XPath compileXPath(RootXPath xPathAnnot) {
		if (xPathAnnot.value() != null) {
			return compileXPath(xPathAnnot.value());
		}

		return null;
	}

	private XPath compileXPath(String xPathString) {
		try {
			return XPathFactory.getInstance().compile(xPathString);
		} catch (SAXPathException e) {
			throw new ClassDefException("The "
					+ FindByXPath.class.getSimpleName()
					+ " annotation on class " + this.getClass().getSimpleName()
					+ " was an invalid XPath expression");
		}
	}

}
