package com.alexgilleran.icesoap.xpath;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.alexgilleran.icesoap.exception.XPathParsingException;
import com.alexgilleran.icesoap.xpath.elements.XPathElement;

public class XPathFactoryTest extends XPathTest {
	private XPathFactory factory = XPathFactory.getInstance();

	@Test
	public void testCompileWithStrings() throws XPathParsingException {
		for (String testXPath : getTestXPaths()) {
			testWithString(testXPath);
		}
	}

	@Test
	public void testRelativeXPath() throws XPathParsingException {
		XPathElement relativeElement = factory
				.compile("this[@pred3=\"value3\"]/is[@pred=\"value\"]/relative[@pred2=\"value2\"]");

		assertEquals("relative", relativeElement.getName());
		assertEquals("value2", relativeElement.getPredicate("pred2"));

		XPathElement isElement = relativeElement.getPreviousElement();
		assertEquals("is", isElement.getName());
		assertEquals("value", isElement.getPredicate("pred"));

		XPathElement thisElement = isElement.getPreviousElement();
		assertEquals("this", thisElement.getName());
		assertEquals("value3", thisElement.getPredicate("pred3"));
	}

	public void testWithString(String xpathString) throws XPathParsingException {
		assertEquals(xpathString, factory.compile(xpathString).toString());
	}

}
