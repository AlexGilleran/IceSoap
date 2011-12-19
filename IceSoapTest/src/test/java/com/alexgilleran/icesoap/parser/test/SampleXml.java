/**
 * 
 */
package com.alexgilleran.icesoap.parser.test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @author Alex Gilleran
 * 
 */
public class SampleXml {
	private final static String PURCHASE_ORDER = // "<?xml version=\"1.0\"?>"
	// + //
	"<aw:PurchaseOrder"
			+ //
			" aw:PurchaseOrderNumber=\"99503\""
			+ //
			" aw:OrderDate=\"1999-10-20\""
			+ //
			" xmlns:aw=\"http://www.adventure-works.com\">"
			+ //
			"<aw:Address aw:Type=\"Shipping\">"
			+ //
			"<aw:Name>Ellen Adams</aw:Name>"
			+ //
			"<aw:Street>123 Maple Street</aw:Street>"
			+ //
			"<aw:City>Mill Valley</aw:City>"
			+ //
			"<aw:State>CA</aw:State>"
			+ //
			"<aw:Zip>10999</aw:Zip>"
			+ //
			"<aw:Country>USA</aw:Country>"
			+ //
			"</aw:Address>"
			+ //
			"<aw:Address aw:Type=\"Billing\">"
			+ //
			"<aw:Name>Tai Yee</aw:Name>"
			+ //
			"<aw:Street>8 Oak Avenue</aw:Street>"
			+ //
			"<aw:City>Old Town</aw:City>"
			+ //
			"<aw:State>PA</aw:State>"
			+ //
			"<aw:Zip>95819</aw:Zip>"
			+ //
			"<aw:Country>USA</aw:Country>"
			+ //
			"</aw:Address>"
			+ //
			"<aw:DeliveryNotes>Please leave packages in shed by driveway.</aw:DeliveryNotes>"
			+ //
			"<aw:Items>" + //
			"<aw:Item aw:PartNumber=\"872-AA\">" + //
			"<aw:ProductName>Lawnmower</aw:ProductName>" + //
			"<aw:Quantity>1</aw:Quantity>" + //
			"<aw:USPrice>148.95</aw:USPrice>" + //
			"<aw:Comment>Confirm this is electric</aw:Comment>" + //
			"</aw:Item>" + //
			"<aw:Item aw:PartNumber=\"926-AA\">" + //
			"<aw:ProductName>Baby Monitor</aw:ProductName>" + //
			"<aw:Quantity>2</aw:Quantity>" + //
			"<aw:USPrice>39.98</aw:USPrice>" + //
			"<aw:ShipDate>1999-05-21</aw:ShipDate>" + //
			"</aw:Item>" + //
			"</aw:Items>" + //
			"</aw:PurchaseOrder>";

	public static InputStream getPurchaseOrder() {
		return new ByteArrayInputStream(PURCHASE_ORDER.getBytes());
	}
}
