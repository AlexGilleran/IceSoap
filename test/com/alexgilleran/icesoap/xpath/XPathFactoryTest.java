package com.alexgilleran.icesoap.xpath;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.alexgilleran.icesoap.exception.XPathParsingException;

public class XPathFactoryTest extends XPathTest {
	private XPathFactory factory = XPathFactory.getInstance();

	@Test
	public void testCompileWithStrings() throws XPathParsingException {
		for (String testXPath : getTestXPaths()) {
			testWithString(testXPath);
		}
	}

	public void testWithString(String xpathString) throws XPathParsingException {
		assertEquals(xpathString, factory.compile(xpathString).toString());
	}

}
