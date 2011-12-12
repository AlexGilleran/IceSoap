package com.alexgilleran.icesoap.xpath;

import static org.junit.Assert.*;

import org.junit.Test;

import com.alexgilleran.icesoap.exception.XPathParsingException;

public class DoubleSlashXPathElementTest extends XPathTest {
	@Test
	public void testMatches() throws XPathParsingException {
		assertTrue(matchStrings("//xpath", "//xpath"));
		assertTrue(matchStrings("//xpath/xpath2", "//xpath/xpath2"));
		assertTrue(matchStrings("//xpath/xpath2", "/foo/xpath/xpath2"));
		assertTrue(matchStrings("//xpath/xpath2",
				"/foo/bar/herp/derp/xpath/xpath2"));
		assertTrue(matchStrings("//xpath/xpath2",
				"/foo/bar/herp[@predicate=\"value\"]/derp/xpath/xpath2"));
		assertTrue(matchStrings("//xpath/xpath2",
		"/foo/bar/herp[@predicate=\"value\"]/derp/xpath/xpath2[@predicate2=\"value2\"]"));
	}

	@Test
	public void testMatchesNegative() throws XPathParsingException {
		assertFalse(matchStrings("//xpath", "/notxpath"));
		assertFalse(matchStrings("//xpath", "/xpath/notxpath"));
	}
}
