package com.alexgilleran.icesoap.xpath.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.alexgilleran.icesoap.exception.XPathParsingException;
import com.alexgilleran.icesoap.xpath.XPathFactory;
import com.alexgilleran.icesoap.xpath.XPathRepository;
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
	public void testXPathUnion() throws XPathParsingException {
		// Test two xpaths linked by a pipe
		XPathRepository<XPathElement> xpaths = factory.compile("/this/is/a/test | hello//everybody");

		XPathRepository<XPathElement> manualXPaths = new XPathRepository<XPathElement>();
		XPathElement firstXPath = factory.compile("/this/is/a/test").keySet().iterator().next();
		XPathElement secondXPath = factory.compile("hello//everybody").keySet().iterator().next();
		manualXPaths.put(firstXPath, firstXPath);
		manualXPaths.put(secondXPath, secondXPath);

		assertEquals(manualXPaths, xpaths);

		// Test three xpaths linked by a pipe.
		xpaths = factory.compile("/this/is/a/test | hello//everybody | //la/de/da");

		XPathElement thirdXPath = factory.compile("//la/de/da").keySet().iterator().next();
		manualXPaths.put(thirdXPath, thirdXPath);

		assertEquals(manualXPaths, xpaths);
	}

	@Test
	public void testRelativeXPath() throws XPathParsingException {
		XPathElement relativeElement = factory
				.compile("this[@pred3=\"value3\"]/is[@pred=\"value\"]/relative[@pred2=\"value2\"]").keySet().iterator()
				.next();

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
		assertEquals(xpathString, factory.compile(xpathString).keySet().iterator().next().toString());
	}

}
