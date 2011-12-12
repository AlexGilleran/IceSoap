package com.alexgilleran.icesoap.xpath;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import com.alexgilleran.icesoap.exception.XPathParsingException;
import com.alexgilleran.icesoap.xpath.elements.XPathElement;

public class XPathRepositoryTest extends XPathTest {
	private XPathRepository<String> repo;

	@Before
	public void setUp() {
		repo = new XPathRepository<String>();
	}

	@Test
	public void testBasic() throws XPathParsingException {
		// Ensure nothing is gettable at the start
		for (String xpathElement : getTestXPaths()) {
			testPut(xpathElement, xpathElement);
		}
	}

	@Test
	public void testSingleSlashes() throws XPathParsingException {
		testPut("/xpath", "/xpath[@predicate=\"predicate\"]");
		testPut("//xpath", "/herp/xpath");
	}

	private void testPut(String xpathToGet, String xpathToPut)
			throws XPathParsingException {
		XPathElement xpathElementToPut = XPathFactory.getInstance().compile(
				xpathToPut);
		XPathElement xpathElementToGet = XPathFactory.getInstance().compile(
				xpathToGet);

		assertNull(repo.get(xpathElementToGet));
		repo.put(xpathElementToPut, xpathToGet);

		assertEquals(xpathToGet, repo.get(xpathElementToPut));
	}
}
