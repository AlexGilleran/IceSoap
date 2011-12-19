/**
 * 
 */
package com.alexgilleran.icesoap.parser.test;

import com.alexgilleran.icesoap.annotation.SOAPField;
import com.alexgilleran.icesoap.annotation.SOAPObject;

@SOAPObject("//Address")
public class Address {
	@SOAPField("Name")
	public String name;
	@SOAPField("//Street")
	public String street;
	@SOAPField("City")
	public String city;
	@SOAPField("Zip")
	public int zip;
	@SOAPField("/PurchaseOrder/Address/Country")
	public String country;
}