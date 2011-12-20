/**
 * 
 */
package com.alexgilleran.icesoap.parser.test.xmlclasses;

import java.util.Date;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

@XMLObject("/PurchaseOrder")
public class PurchaseOrder {
	@XMLField("@PurchaseOrderNumber")
	public long purchaseOrderNumber;
	@XMLField("@OrderDate")
	public Date orderDate;
	@XMLField("Address[@Type=\"Shipping\"]")
	public Address shippingAddress;
	@XMLField("/PurchaseOrder/Address[@Type=\"Billing\"]")
	public Address billingAddress;
	@XMLField("DeliveryNotes")
	public String deliveryNotes;
	@XMLField("Items/Item[@PartNumber=\"872-AA\"]")
	public Item item872aa;
	@XMLField("Items/Item[@PartNumber=\"926-AA\"]")
	public Item item926aa;
}