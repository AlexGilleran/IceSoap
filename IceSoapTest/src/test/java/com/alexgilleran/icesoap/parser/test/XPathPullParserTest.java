/**
 * 
 */
package com.alexgilleran.icesoap.parser.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.xmlpull.v1.XmlPullParserException;

import com.alexgilleran.icesoap.exception.XmlParsingException;
import com.alexgilleran.icesoap.parser.XPathPullParser;
import com.alexgilleran.icesoap.parser.impl.XPathPullParserImpl;
import com.alexgilleran.icesoap.xpath.elements.XPathElement;
import com.alexgilleran.icesoap.xpath.elements.impl.AttributeXPathElement;
import com.alexgilleran.icesoap.xpath.elements.impl.SingleSlashXPathElement;
import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * Tests {@link XPathPullParserImpl}
 * 
 * @author Alex Gilleran
 * 
 */
@RunWith(RobolectricTestRunner.class)
public class XPathPullParserTest {
	/**
	 * Holistic test - tests xml nodes, text elements, attributes and
	 * predicates.
	 * 
	 * @throws XmlPullParserException
	 * @throws XmlParsingException
	 */
	@Test
	public void testWithPurchaseOrder() throws XmlPullParserException,
			XmlParsingException {
		// Get a new instance of pull parser and set the input to the Microsoft
		// Purchase Order sample XML
		XPathPullParser parser = new XPathPullParserImpl();
		parser.setInput(SampleXml.getPurchaseOrder(), null);

		// Purchase Order Node (root)
		XPathElement expectedXPathElement = new SingleSlashXPathElement(
				"PurchaseOrder", null);
		expectedXPathElement.addPredicate("PurchaseOrderNumber", "99503");
		expectedXPathElement.addPredicate("OrderDate", "1999-10-20");

		assertEquals(XPathPullParser.START_TAG, parser.next());
		assertEquals(expectedXPathElement, parser.getCurrentElement());

		// Purchase Order "PurchaseOrderNumber" Attribute
		assertAttribute(parser, expectedXPathElement, "PurchaseOrderNumber",
				"99503");

		// Purchase Order "OrderDate" Attribute
		assertAttribute(parser, expectedXPathElement, "OrderDate", "1999-10-20");

		// Address Node
		expectedXPathElement = new SingleSlashXPathElement("Address",
				expectedXPathElement);
		expectedXPathElement.addPredicate("Type", "Shipping");

		// Shipping Address
		assertEquals(XPathPullParser.START_TAG, parser.next());
		assertEquals(expectedXPathElement, parser.getCurrentElement());

		assertAttribute(parser, expectedXPathElement, "Type", "Shipping");
		assertTextElement(parser, expectedXPathElement, "Name", "Ellen Adams");
		assertTextElement(parser, expectedXPathElement, "Street",
				"123 Maple Street");
		assertTextElement(parser, expectedXPathElement, "City", "Mill Valley");
		assertTextElement(parser, expectedXPathElement, "State", "CA");
		assertTextElement(parser, expectedXPathElement, "Zip", "10999");
		assertTextElement(parser, expectedXPathElement, "Country", "USA");

		assertEquals(XPathPullParser.END_TAG, parser.next());
		assertEquals(expectedXPathElement, parser.getCurrentElement());

		assertEquals(XPathPullParser.START_TAG, parser.next());
		// Adding another type predicate will override the previous one
		expectedXPathElement.addPredicate("Type", "Billing");
		assertEquals(expectedXPathElement, parser.getCurrentElement());

		// Billing Address
		assertAttribute(parser, expectedXPathElement, "Type", "Billing");
		assertTextElement(parser, expectedXPathElement, "Name", "Tai Yee");
		assertTextElement(parser, expectedXPathElement, "Street",
				"8 Oak Avenue");
		assertTextElement(parser, expectedXPathElement, "City", "Old Town");
		assertTextElement(parser, expectedXPathElement, "State", "PA");
		assertTextElement(parser, expectedXPathElement, "Zip", "95819");
		assertTextElement(parser, expectedXPathElement, "Country", "USA");

		assertEquals(XPathPullParser.END_TAG, parser.next());
		assertEquals(expectedXPathElement, parser.getCurrentElement());

		expectedXPathElement = expectedXPathElement.getPreviousElement();

		assertTextElement(parser, expectedXPathElement, "DeliveryNotes",
				"Please leave packages in shed by driveway.");
	}

	/**
	 * Moves the parser into a subsequent text element and asserts that its name
	 * and value are correct
	 * 
	 * @param parser
	 *            The xmlpullparser to move
	 * @param rootXPathElement
	 *            The element that the text node is a part of
	 * @param name
	 *            The name of the text element
	 * @param value
	 *            The value of the text element
	 * @throws XmlParsingException
	 */
	private void assertTextElement(XPathPullParser parser,
			XPathElement rootXPathElement, String name, String value)
			throws XmlParsingException {
		assertEquals(XPathPullParser.START_TAG, parser.next());
		XPathElement textXPath = new SingleSlashXPathElement(name,
				rootXPathElement);
		assertEquals(textXPath, parser.getCurrentElement());
		assertEquals(parser.getCurrentValue(), value);
	}

	/**
	 * Moves the parser into a subsequent attribute and asserts that its name
	 * and value are correct
	 * 
	 * @param parser
	 *            The xmlpullparser to move
	 * @param node
	 *            The element that the attribute is a part of
	 * @param name
	 *            The name of the attribute
	 * @param value
	 *            The value of the attribute
	 * @throws XmlParsingException
	 */
	private void assertAttribute(XPathPullParser parser, XPathElement node,
			String name, String value) throws XmlParsingException {
		assertEquals(XPathPullParser.ATTRIBUTE, parser.next());
		assertEquals(new AttributeXPathElement(name, node),
				parser.getCurrentElement());
		assertEquals(value, parser.getCurrentValue());
	}
}
