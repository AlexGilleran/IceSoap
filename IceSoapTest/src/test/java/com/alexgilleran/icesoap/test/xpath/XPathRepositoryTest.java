package com.alexgilleran.icesoap.test.xpath;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import com.alexgilleran.icesoap.exception.XPathParsingException;
import com.alexgilleran.icesoap.xpath.XPathFactory;
import com.alexgilleran.icesoap.xpath.XPathRepository;
import com.alexgilleran.icesoap.xpath.elements.XPathElement;

public class XPathRepositoryTest extends XPathTest {
	private XPathRepository<String> repo;

	@Before
	public void setUp() {
		repo = new XPathRepository<String>();
	}

	@Test
	public void testBasic() throws XPathParsingException {
		for (String xpathElement : getTestXPaths()) {
			testPut(xpathElement, xpathElement);
		}
	}

	@Test
	public void testSingleSlashes() throws XPathParsingException {
		testPut("/xpath", "/xpath");
		testPut("/xpath", "/xpath[@predicate=\"predicate\"]");
	}

	@Test
	public void testDoubleSlashes() throws XPathParsingException {
		testPut("//xpath", "/herp/xpath");
		testPut("//xpath", "/herp/xpath[@predicate=\"predicate\"]");
	}

	@Test
	public void testPredicateSelection() throws XPathParsingException {
		final String xpath1 = "/xpath1/xpath2";
		final String xpath2 = "/xpath1/xpath2[@predicate=\"wrongvalue\"]";
		final String xpath3 = "/xpath1/xpath2[@predicate=\"rightvalue\"]";

		repo.put(XPathFactory.getInstance().compile(xpath1), xpath1);
		repo.put(XPathFactory.getInstance().compile(xpath2), xpath2);
		repo.put(XPathFactory.getInstance().compile(xpath3), xpath3);

		assertEquals(repo.get(XPathFactory.getInstance().compile(xpath1)),
				xpath1);
		assertEquals(repo.get(XPathFactory.getInstance().compile(xpath2)),
				xpath2);
		assertEquals(repo.get(XPathFactory.getInstance().compile(xpath3)),
				xpath3);
	}

	@Test
	public void testAttributeSelection() throws XPathParsingException {
		final String nodeXPath = "/xpath1/xpath";
		final String attributeXPath = "/xpath1/@xpath";

		repo.put(XPathFactory.getInstance().compile(nodeXPath), nodeXPath);
		repo.put(XPathFactory.getInstance().compile(attributeXPath),
				attributeXPath);

		assertEquals(repo.get(XPathFactory.getInstance().compile(nodeXPath)),
				nodeXPath);
		assertEquals(
				repo.get(XPathFactory.getInstance().compile(attributeXPath)),
				attributeXPath);
	}

	private void testPut(String xpathToGet, String xpathToPut)
			throws XPathParsingException {
		repo = new XPathRepository<String>();
		XPathElement xpathElementToPut = XPathFactory.getInstance().compile(
				xpathToPut);
		XPathElement xpathElementToGet = XPathFactory.getInstance().compile(
				xpathToGet);

		assertNull(repo.get(xpathElementToGet));
		repo.put(xpathElementToPut, xpathToGet);

		assertEquals(xpathToGet, repo.get(xpathElementToPut));
	}
}
