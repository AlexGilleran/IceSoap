/**
 * 
 */
package com.alexgilleran.icesoap.parser.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.xmlpull.v1.XmlPullParserException;

import com.alexgilleran.icesoap.exception.XmlParsingException;
import com.alexgilleran.icesoap.parser.IceSoapParser;
import com.alexgilleran.icesoap.parser.impl.IceSoapParserImpl;
import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * @author Alex Gilleran
 * 
 */
@RunWith(RobolectricTestRunner.class)
public class IceSoapParserTest {

	@Test
	public void testPurchaseOrder() throws XmlPullParserException,
			XmlParsingException {
		IceSoapParser<PurchaseOrder> parser = new IceSoapParserImpl<PurchaseOrder>(
				PurchaseOrder.class);

		PurchaseOrder po = parser.parse(SampleXml.getPurchaseOrder());

		assertEquals(99503, po.purchaseOrderNumber);
	}
}
