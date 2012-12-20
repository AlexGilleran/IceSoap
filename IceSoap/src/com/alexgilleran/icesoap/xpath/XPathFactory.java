package com.alexgilleran.icesoap.xpath;

import java.util.List;

import org.jaxen.saxpath.Axis;
import org.jaxen.saxpath.SAXPathException;
import org.jaxen.saxpath.XPathHandler;
import org.jaxen.saxpath.XPathReader;
import org.jaxen.saxpath.helpers.XPathReaderFactory;

import com.alexgilleran.icesoap.exception.XPathParsingException;
import com.alexgilleran.icesoap.xpath.elements.XPathElement;
import com.alexgilleran.icesoap.xpath.elements.impl.AttributeXPathElement;
import com.alexgilleran.icesoap.xpath.elements.impl.DoubleSlashXPathElement;
import com.alexgilleran.icesoap.xpath.elements.impl.RelativeXPathElement;
import com.alexgilleran.icesoap.xpath.elements.impl.SingleSlashXPathElement;

/**
 * Used to compile XPaths represented as Strings into IceSoap java
 * representations, using the Jaxen XPathHandler.
 * 
 * Note that this is a singleton - call getInstance() to get an instance rather
 * than constructing it.
 * 
 * @author Alex Gilleran
 * 
 */
public class XPathFactory {
	/** Instance of the Singleton class */
	private static XPathFactory INSTANCE = null;

	/**
	 * Only used by getInstance() as this is a singleton.
	 */
	private XPathFactory() {
	}

	/**
	 * Gets an instance of the XPathFactory.
	 * 
	 * @return the Singleton instance of the XPathFactory.
	 */
	public static XPathFactory getInstance() {
		// Lazily initialize the instance.
		if (INSTANCE == null) {
			INSTANCE = new XPathFactory();
		}

		return INSTANCE;
	}

	/**
	 * Compiles an XPath expression in String format to IceSoap java object (
	 * {@link XPathElement}) format.
	 * 
	 * Note that this returns a repository of XPathElements in case a union ('|'
	 * or pipe) operator is encountered - if there isn't one, the list will only
	 * have one item at index 0.
	 * 
	 * @param xpathString
	 *            The xpath expression to compile, as a String.
	 * @return A repository of the XPaths in the compiled expression.
	 * @throws XPathParsingException
	 *             Will occur if the passed XPath is invalid, or uses features
	 *             that are currently unsupported.
	 */
	public XPathRepository<XPathElement> compile(String xpathString) throws XPathParsingException {
		try {
			XPathReader reader = XPathReaderFactory.createReader();

			IceSoapXPathHandler handler = new IceSoapXPathHandler();
			reader.setXPathHandler(handler);
			reader.parse(xpathString);

			return handler.getXPaths();
		} catch (SAXPathException e) {
			// This will occur in the event of an invalid XPath - wrap with an
			// IceSoap exception type and rethrow
			throw new XPathParsingException(e);
		}
	}

	/**
	 * Implementation of Jaxen XPathHandler used to parse XPaths to the IceSoap
	 * format.
	 * 
	 * As only a subset of XPath is supported by IceSoap, many methods simply do
	 * nothing.
	 * 
	 * @author Alex Gilleran
	 * 
	 */
	private class IceSoapXPathHandler implements XPathHandler {
		/**
		 * Holds the current element being passed - this will eventually be
		 * added to the list.
		 */
		private XPathElement currentElement;
		/**
		 * Repo of XPaths that have been got from the current expression (there
		 * can be multiple xpaths retrieved from xpath expressions that use the
		 * | operator).
		 * 
		 * We use the {@link XPathRepository} rather than something like a
		 * {@link List} in order to facilitate faster lookups in the parsers.
		 */
		private XPathRepository<XPathElement> xpaths = new XPathRepository<XPathElement>();
		/** Whether a predicate expression is currently being parsed. */
		private boolean currentlyParsingPredicate = false;
		/** Whether we are currently parsing a double-slash XPath element. */
		private boolean allNodeStep = false;
		/**
		 * Whether we're currently parsing a relative element - one that starts
		 * with no slashes.
		 */
		private boolean relativeElement = false;
		/** The key (name) of the current predicate. */
		private String currentPredicateKey;
		/**
		 * Remembers to create a detached element on the next name event rather
		 * than attaching the new element to the old xpath.
		 */
		private boolean detachNextElement = true;

		/**
		 * Gets the XPath that has been parsed as a result of calls made to this
		 * object by an XPathReader.
		 * 
		 * @return The final element in the last XPath.
		 */
		public XPathRepository<XPathElement> getXPaths() {
			return xpaths;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * <p>
		 * Called when the reader encounters an element with a name - this could
		 * be an attribute, a predicate or a node.
		 * </p>
		 */
		@Override
		public void startNameStep(int axis, String prefix, String localName) throws SAXPathException {
			if (currentlyParsingPredicate) {
				currentPredicateKey = localName;
			} else {
				if (detachNextElement) {
					currentElement = null;
					detachNextElement = false;
				}

				// Determine what kind of element we're parsing relative,
				// singleslash, doubleslash) based on the previously called
				// methods and the flags they've set.
				if (allNodeStep) {
					currentElement = new DoubleSlashXPathElement(localName, currentElement);
					allNodeStep = false;
				} else if (relativeElement) {
					currentElement = new RelativeXPathElement(localName, currentElement);
					relativeElement = false;
				} else {
					currentElement = new SingleSlashXPathElement(localName, currentElement);
				}

				if (axis == Axis.ATTRIBUTE) {
					currentElement = new AttributeXPathElement(currentElement);
				}
			}
		}

		/**
		 * {@inheritDoc}
		 * 
		 * <p>
		 * Called when a predicate ("[") expression is encountered.
		 * </p>
		 */
		@Override
		public void startPredicate() throws SAXPathException {
			// Set a flag so that when the predicate name/value is encountered,
			// we know what to do.
			currentlyParsingPredicate = true;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * <p>
		 * Called when an = is encountered - passes the value of the =
		 * expression.
		 * </p>
		 */
		@Override
		public void literal(String value) throws SAXPathException {

			if (currentlyParsingPredicate && currentPredicateKey != null) {
				// Currently we only use this for predicates. At this point we
				// would have had a namestep call to remember the predicate
				// name, so add the predicate with that name and the value
				// passed into this method.
				currentElement.addPredicate(currentPredicateKey, value);
			}
		}

		/**
		 * {@inheritDoc}
		 * 
		 * <p>
		 * Called when a predicate expression is ended ("]").
		 * </p>
		 */
		@Override
		public void endPredicate() throws SAXPathException {
			// Set the flag to false and nullify the key.
			currentlyParsingPredicate = false;
			currentPredicateKey = null;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * Called when a relative (starts with no slash) path is started.
		 */
		@Override
		public void startRelativeLocationPath() throws SAXPathException {
			// Flag this so we know to create a relative element for the first
			// element.
			relativeElement = true;
		}

		@Override
		public void startAllNodeStep(int arg0) throws SAXPathException {
			allNodeStep = true;
		}

		@Override
		public void endXPath() throws SAXPathException {
			xpaths.put(currentElement, currentElement);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * Called when a '|' operator is started.
		 */
		@Override
		public void startUnionExpr() throws SAXPathException {
			// For some reason this gets called not only on '|' operators but
			// also right at the beginning and when predicates are encountered -
			// filter these events out.
			if (currentElement != null && !currentlyParsingPredicate) {
				detachNextElement = true;
				xpaths.put(currentElement, currentElement);
			}
		}

		@Override
		public void endUnionExpr(boolean create) throws SAXPathException {
			// Currently unused.
		}

		@Override
		public void endNameStep() throws SAXPathException {
			// Currently unused.
		}

		@Override
		public void startAbsoluteLocationPath() throws SAXPathException {
			// Currently unused.
		}

		@Override
		public void endAbsoluteLocationPath() throws SAXPathException {
			// Currently unused

		}

		@Override
		public void endAdditiveExpr(int arg0) throws SAXPathException {
			// Currently unused

		}

		@Override
		public void endAllNodeStep() throws SAXPathException {
			// Currently unused.
		}

		@Override
		public void endAndExpr(boolean arg0) throws SAXPathException {
			// Currently unused

		}

		@Override
		public void endCommentNodeStep() throws SAXPathException {
			// Currently unused
		}

		@Override
		public void endEqualityExpr(int arg0) throws SAXPathException {
			// Currently unused
		}

		@Override
		public void endFilterExpr() throws SAXPathException {
			// Currently unused
		}

		@Override
		public void endFunction() throws SAXPathException {
			// Currently unused
		}

		@Override
		public void endMultiplicativeExpr(int arg0) throws SAXPathException {
			// Currently unused
		}

		@Override
		public void endOrExpr(boolean arg0) throws SAXPathException {
			// Currently unused
		}

		@Override
		public void endPathExpr() throws SAXPathException {
			// Currently unused
		}

		@Override
		public void endProcessingInstructionNodeStep() throws SAXPathException {
			// Currently unused
		}

		@Override
		public void endRelationalExpr(int arg0) throws SAXPathException {
			// Currently unused
		}

		@Override
		public void endRelativeLocationPath() throws SAXPathException {
			// Currently unused
		}

		@Override
		public void endTextNodeStep() throws SAXPathException {
			// Currently unused
		}

		@Override
		public void endUnaryExpr(int arg0) throws SAXPathException {
			// Currently unused
		}

		@Override
		public void number(int arg0) throws SAXPathException {
			// Currently unused
		}

		@Override
		public void number(double arg0) throws SAXPathException {
			// Currently unused
		}

		@Override
		public void startAdditiveExpr() throws SAXPathException {
			// Currently unused
		}

		@Override
		public void startAndExpr() throws SAXPathException {
			// Currently unused
		}

		@Override
		public void startCommentNodeStep(int arg0) throws SAXPathException {
			// Currently unused
		}

		@Override
		public void startEqualityExpr() throws SAXPathException {
			// Currently unused
		}

		@Override
		public void startFilterExpr() throws SAXPathException {
			// Currently unused
		}

		@Override
		public void startFunction(String arg0, String arg1) throws SAXPathException {
			// Currently unused
		}

		@Override
		public void startMultiplicativeExpr() throws SAXPathException {
			// Currently unused
		}

		@Override
		public void startOrExpr() throws SAXPathException {
			// Currently unused
		}

		@Override
		public void startPathExpr() throws SAXPathException {
			// Currently unused
		}

		@Override
		public void startProcessingInstructionNodeStep(int arg0, String arg1) throws SAXPathException {
			// Currently unused
		}

		@Override
		public void startRelationalExpr() throws SAXPathException {
			// Currently unused
		}

		@Override
		public void startTextNodeStep(int arg0) throws SAXPathException {
			// Currently unused
		}

		@Override
		public void startUnaryExpr() throws SAXPathException {
			// Currently unused
		}

		@Override
		public void startXPath() throws SAXPathException {
			// Currently unused
		}

		@Override
		public void variableReference(String arg0, String arg1) throws SAXPathException {
			// Currently unused
		}
	}
}
