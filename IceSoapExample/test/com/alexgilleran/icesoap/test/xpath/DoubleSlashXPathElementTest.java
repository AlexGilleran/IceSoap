package com.alexgilleran.icesoap.test.xpath;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.alexgilleran.icesoap.exception.XPathParsingException;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
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
