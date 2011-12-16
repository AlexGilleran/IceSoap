package com.alexgilleran.icesoap.test.xpath;

import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.alexgilleran.icesoap.exception.XPathParsingException;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class XPathElementTest extends XPathTest {

	@Test
	public void testMatchesAgainstSelf() throws XPathParsingException {
		for (String testXPath : getTestXPaths()) {
			testAgainstSelf(testXPath);
		}
	}

	@Test
	public void testDoesntMatchBasic() throws XPathParsingException {
		String[] unmatchableXPaths = { "/this/wont/match/anything",
				"/an/@attribute" };

		for (String unmatchablePath : unmatchableXPaths) {
			for (String testXPath : getTestXPaths()) {
				assertFalse(matchStrings(unmatchablePath, testXPath));
			}
		}
	}
}
