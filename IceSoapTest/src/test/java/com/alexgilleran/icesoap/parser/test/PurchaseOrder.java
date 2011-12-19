/**
 * 
 */
package com.alexgilleran.icesoap.parser.test;

import java.util.Date;

import com.alexgilleran.icesoap.annotation.SOAPField;
import com.alexgilleran.icesoap.annotation.SOAPObject;

@SOAPObject("/PurchaseOrder")
public class PurchaseOrder {
	@SOAPField("@PurchaseOrderNumber")
	public int purchaseOrderNumber;
	@SOAPField("@OrderDate")
	public Date orderDate;
	@SOAPField("Address[@Type=\"Shipping\"]")
	public Address shippingAddress;
	@SOAPField("/PurchaseOrder/Address[@Type=\"Billing\"]")
	public Address billingAddress;
	@SOAPField("DeliveryNotes")
	public String deliveryNotes;
	
	public PurchaseOrder()
	{
		
	}
}