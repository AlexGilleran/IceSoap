package com.alexgilleran.icesoap.xpath.test;

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
		final String xpathNoPred = "/xpath1/xpath2";
		final String xpathWrongValuePred = "/xpath1/xpath2[@predicate=\"wrongvalue\"]";
		final String xpathRightValuePred = "/xpath1/xpath2[@predicate=\"rightvalue\"]";

		repo.put(XPathFactory.getInstance().compile(xpathNoPred).keySet().iterator().next(), xpathNoPred);
		repo.put(XPathFactory.getInstance().compile(xpathWrongValuePred).keySet().iterator().next(), xpathWrongValuePred);
		repo.put(XPathFactory.getInstance().compile(xpathRightValuePred).keySet().iterator().next(), xpathRightValuePred);

		assertEquals(xpathNoPred, repo.get(XPathFactory.getInstance().compile(xpathNoPred).keySet().iterator().next()));
		assertEquals(xpathWrongValuePred, repo.get(XPathFactory.getInstance().compile(xpathWrongValuePred).keySet().iterator().next()));
		assertEquals(xpathRightValuePred, repo.get(XPathFactory.getInstance().compile(xpathRightValuePred).keySet().iterator().next()));
	}

	@Test
	public void testAttributeSelection() throws XPathParsingException {
		final String nodeXPath = "/xpath1/xpath";
		final String attributeXPath = "/xpath1/@xpath";

		repo.put(XPathFactory.getInstance().compile(nodeXPath).keySet().iterator().next(), nodeXPath);
		repo.put(XPathFactory.getInstance().compile(attributeXPath).keySet().iterator().next(), attributeXPath);

		assertEquals(repo.get(XPathFactory.getInstance().compile(nodeXPath).keySet().iterator().next()), nodeXPath);
		assertEquals(repo.get(XPathFactory.getInstance().compile(attributeXPath).keySet().iterator().next()),
				attributeXPath);
	}

	@Test
	public void testRemove() throws XPathParsingException {
		XPathElement xpath1 = XPathFactory.getInstance().compile("//this/is/a/test").keySet().iterator().next();
		XPathElement xpath2 = XPathFactory.getInstance().compile("/blah/herp").keySet().iterator().next();
		XPathElement xpath3 = XPathFactory.getInstance().compile("//test").keySet().iterator().next();

		repo.put(xpath1, xpath1.toString());
		repo.put(xpath2, xpath2.toString());
		repo.put(xpath3, xpath3.toString());

		assertEquals(3, repo.size());

		repo.remove(xpath2);

		assertEquals(2, repo.size());

		assertEquals(xpath1.toString(), repo.get(xpath1));
		assertNull(repo.get(xpath2));
		assertEquals(xpath3.toString(), repo.get(xpath3));
	}

	@Test
	public void testRemoveEnsureExactMatch() throws XPathParsingException {
		XPathElement xpath1 = XPathFactory.getInstance().compile("//test/is/a/this").keySet().iterator().next();
		XPathElement xpath2 = XPathFactory.getInstance().compile("/a/this").keySet().iterator().next();
		XPathElement xpath3 = XPathFactory.getInstance().compile("//this").keySet().iterator().next();

		repo.put(xpath1, xpath1.toString());
		repo.put(xpath2, xpath2.toString());
		repo.put(xpath3, xpath3.toString());

		repo.remove(xpath1);

		assertEquals(xpath2.toString(), repo.get(xpath2));
		assertEquals(xpath3.toString(), repo.get(xpath3));
	}

	private void testPut(String xpathToGet, String xpathToPut) throws XPathParsingException {
		repo = new XPathRepository<String>();
		XPathElement xpathElementToPut = XPathFactory.getInstance().compile(xpathToPut).keySet().iterator().next();
		XPathElement xpathElementToGet = XPathFactory.getInstance().compile(xpathToGet).keySet().iterator().next();

		assertNull(repo.get(xpathElementToGet));
		repo.put(xpathElementToPut, xpathToGet);

		assertEquals(xpathToGet, repo.get(xpathElementToPut));
	}
}
