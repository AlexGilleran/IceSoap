/**
 * 
 */
package com.alexgilleran.icesoap.parser.test;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.xmlpull.v1.XmlPullParserException;

import com.alexgilleran.icesoap.exception.XmlParsingException;
import com.alexgilleran.icesoap.parser.IceSoapParser;
import com.alexgilleran.icesoap.parser.impl.IceSoapParserImpl;
import com.alexgilleran.icesoap.parser.test.xmlclasses.PurchaseOrder;
import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * @author Alex Gilleran
 * 
 */
@RunWith(RobolectricTestRunner.class)
public class IceSoapParserTest {
	SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * Holistic test on realistic data.
	 * 
	 * @throws XmlPullParserException
	 * @throws XmlParsingException
	 * @throws ParseException
	 */
	@Test
	public void testPurchaseOrder() throws XmlPullParserException,
			XmlParsingException, ParseException {
		IceSoapParser<PurchaseOrder> parser = new IceSoapParserImpl<PurchaseOrder>(
				PurchaseOrder.class);

		PurchaseOrder po = parser.parse(SampleXml.getPurchaseOrder());

		assertEquals(99503l, po.purchaseOrderNumber);
		assertEquals(FORMAT.parse("1999-10-20"), po.orderDate);

		// Shipping Address
		assertEquals("Shipping", po.shippingAddress.type);
		assertEquals("Ellen Adams", po.shippingAddress.name);
		assertEquals("123 Maple Street", po.shippingAddress.street);
		assertEquals("Mill Valley", po.shippingAddress.city);
		assertEquals("CA", po.shippingAddress.state);
		assertEquals(10999, po.shippingAddress.zip);
		assertEquals("USA", po.shippingAddress.country);

		// Billing Address
		assertEquals("Billing", po.billingAddress.type);
		assertEquals("Tai Yee", po.billingAddress.name);
		assertEquals("8 Oak Avenue", po.billingAddress.street);
		assertEquals("Old Town", po.billingAddress.city);
		assertEquals("PA", po.billingAddress.state);
		assertEquals(95819, po.billingAddress.zip);
		assertEquals("USA", po.billingAddress.country);

		assertEquals("Please leave packages in shed by driveway.",
				po.deliveryNotes);

		// Item 1
		assertEquals("872-AA", po.item872aa.partNumber);
		assertEquals("Lawnmower", po.item872aa.productName);
		assertEquals(1d, po.item872aa.quantity, 0d);
		assertEquals(new BigDecimal("148.95"), po.item872aa.usPrice);
		assertEquals(null, po.item872aa.shipDate);
		assertEquals("Confirm this is electric", po.item872aa.comment);

		// Item 2
		assertEquals("926-AA", po.item926aa.partNumber);
		assertEquals("Baby Monitor", po.item926aa.productName);
		assertEquals(2d, po.item926aa.quantity, 0d);
		assertEquals(new BigDecimal("39.98"), po.item926aa.usPrice);
		assertEquals(FORMAT.parse("1999-05-21"), po.item926aa.shipDate);
		assertEquals(null, po.item926aa.comment);
	}
}
