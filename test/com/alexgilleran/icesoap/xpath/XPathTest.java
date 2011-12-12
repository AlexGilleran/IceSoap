package com.alexgilleran.icesoap.xpath;

import static org.junit.Assert.assertTrue;

import com.alexgilleran.icesoap.exception.XPathParsingException;

public class XPathTest {

	protected String[] getTestXPaths() {
		return new String[] { //
		"/example",//
				"/example1/example2",//
				"/example1/example2/example3",//
				"//example1",//
				"//example1/example2",//
				"//example1/example2/example3",//
				"/example1//example2/example3",//
				"/example1/example2//example3",//
				"/example1/@attribute",//
				"/example1/example2/@attribute",//
				"/example1//example2/@attribute",//
				"/example1[@predicate=\"value\"]",//
				"/example1/example2[@predicate=\"value\"]" };
	}

	protected void testAgainstSelf(String xpathString)
			throws XPathParsingException {
		assertTrue(xpathString + " doesn't match itself",
				matchStrings(xpathString, xpathString));
	}

	protected boolean matchStrings(String xpath1, String xpath2)
			throws XPathParsingException {
		return XPathFactory.getInstance().compile(xpath1)
				.matches(XPathFactory.getInstance().compile(xpath2));
	}
}
