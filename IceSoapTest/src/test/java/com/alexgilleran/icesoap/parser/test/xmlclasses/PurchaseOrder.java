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
	private long purchaseOrderNumber;
	@XMLField("@OrderDate")
	private Date orderDate;
	@XMLField("Address[@Type=\"Shipping\"]")
	private Address shippingAddress;
	@XMLField("/PurchaseOrder/Address[@Type=\"Billing\"]")
	private Address billingAddress;
	@XMLField("DeliveryNotes")
	private String deliveryNotes;
	@XMLField("Items/Item[@PartNumber=\"872-AA\"]")
	private Item item872aa;
	@XMLField("Items/Item[@PartNumber=\"926-AA\"]")
	private Item item926aa;

	public long getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public Address getBillingAddress() {
		return billingAddress;
	}

	public String getDeliveryNotes() {
		return deliveryNotes;
	}

	public Item getItem872aa() {
		return item872aa;
	}

	public Item getItem926aa() {
		return item926aa;
	}
}