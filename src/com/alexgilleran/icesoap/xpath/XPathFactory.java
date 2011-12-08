package com.alexgilleran.icesoap.xpath;

import org.jaxen.saxpath.SAXPathException;
import org.jaxen.saxpath.XPathHandler;
import org.jaxen.saxpath.XPathReader;
import org.jaxen.saxpath.helpers.XPathReaderFactory;

import android.util.Log;

public class XPathFactory {
	private static XPathFactory INSTANCE = null;

	private void XPathFactory() {

	}

	public static XPathFactory getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new XPathFactory();
		}

		return INSTANCE;
	}

	public XPath compile(String xPathString) throws SAXPathException {
		XPathReader reader = XPathReaderFactory.createReader();

		AndroidXPathHandler handler = new AndroidXPathHandler();
		reader.setXPathHandler(handler);

		reader.parse(xPathString);

		return handler.getXPath();
	}

	private class AndroidXPathHandler implements XPathHandler {
		XPath xPath;
		XPathElement currentElement;

		boolean currentlyParsingPredicate = false;;

		String currentPredicateKey;

		public AndroidXPathHandler() {
			xPath = new XPath();
		}

		public XPath getXPath() {
			return xPath;
		}

		@Override
		public void startNameStep(int axis, String prefix, String localName)
				throws SAXPathException {
			if (currentlyParsingPredicate) {
				currentPredicateKey = localName;
			} else {
				currentElement = new XPathElement(localName);
			}
		}

		@Override
		public void endNameStep() throws SAXPathException {
			if (!currentlyParsingPredicate) {
				xPath.addElement(currentElement);
				currentElement = null;
			}
		}

		@Override
		public void startPredicate() throws SAXPathException {
			currentlyParsingPredicate = true;
		}

		@Override
		public void endPredicate() throws SAXPathException {
			currentlyParsingPredicate = false;
			currentPredicateKey = null;
		}

		@Override
		public void literal(String value) throws SAXPathException {
			if (currentlyParsingPredicate && currentPredicateKey != null) {
				currentElement.addPredicate(currentPredicateKey, value);
			}
		}

		@Override
		public void startAbsoluteLocationPath() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void endAbsoluteLocationPath() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void endAdditiveExpr(int arg0) throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void endAllNodeStep() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void endAndExpr(boolean arg0) throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void endCommentNodeStep() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void endEqualityExpr(int arg0) throws SAXPathException {
			Log.d("hello", "hello");
		}

		@Override
		public void endFilterExpr() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void endFunction() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void endMultiplicativeExpr(int arg0) throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void endOrExpr(boolean arg0) throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void endPathExpr() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void endProcessingInstructionNodeStep() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void endRelationalExpr(int arg0) throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void endRelativeLocationPath() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void endTextNodeStep() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void endUnaryExpr(int arg0) throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void endUnionExpr(boolean arg0) throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void endXPath() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void number(int arg0) throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void number(double arg0) throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void startAdditiveExpr() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void startAllNodeStep(int arg0) throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void startAndExpr() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void startCommentNodeStep(int arg0) throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void startEqualityExpr() throws SAXPathException {
			Log.d("hello", "hello");
		}

		@Override
		public void startFilterExpr() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void startFunction(String arg0, String arg1)
				throws SAXPathException {
			Log.d("hello", "hello");
		}

		@Override
		public void startMultiplicativeExpr() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void startOrExpr() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void startPathExpr() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void startProcessingInstructionNodeStep(int arg0, String arg1)
				throws SAXPathException {
			Log.d("hello", "hello");
		}

		@Override
		public void startRelationalExpr() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void startRelativeLocationPath() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void startTextNodeStep(int arg0) throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void startUnaryExpr() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void startUnionExpr() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void startXPath() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void variableReference(String arg0, String arg1)
				throws SAXPathException {
			Log.d("hello", "hello");
		}

	}
}
