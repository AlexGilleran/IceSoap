package com.alexgilleran.icesoap.xpath;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.alexgilleran.icesoap.xpath.elements.SingleSlashXPElement;

public class XPathTest {
	XPath blankXPath;
	XPath testXPath;

	@Before
	public void setUp() {
		blankXPath = new XPath();
		testXPath = new XPath();

//		testXPath.addElement(new XPathElement("Test1"));
//		testXPath.addElement(new XPathElement("Test2"));
//		testXPath.addElement(new XPathElement("Test3"));
	}

	@Test
	public void testAddElement() {//TODO
		blankXPath.addElement(new SingleSlashXPElement("Hello", false, null));
		assertEquals("/Hello", blankXPath.toString());
	}

	@Test
	public void testRemoveElement() {
		testXPath.removeElement();
		assertEquals(testXPath.toString(), "/Test1/Test2");
	}

	@Test
	public void testToString() {
		assertEquals(testXPath.toString(), "/Test1/Test2/Test3");
	}

	@Test
	public void testMatchesBasic() {
		XPath matchingTestXPath = new XPath();
//		matchingTestXPath.addElement(new XPathElement("Test1"));
//		matchingTestXPath.addElement(new XPathElement("Test2"));
//		matchingTestXPath.addElement(new XPathElement("Test3"));

		assertTrue(testXPath.matches(matchingTestXPath));
		assertTrue(matchingTestXPath.matches(testXPath));

		XPath notMatchingTestXPath = new XPath();
//		notMatchingTestXPath.addElement(new XPathElement("Test1"));
//		notMatchingTestXPath.addElement(new XPathElement("Test5"));
//		notMatchingTestXPath.addElement(new XPathElement("Test3"));

		assertFalse(testXPath.matches(notMatchingTestXPath));
		assertFalse(notMatchingTestXPath.matches(testXPath));
	}

}
