/**
 * 
 */
package com.alexgilleran.icesoap.parser.test.xmlclasses;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

@XMLObject("//Address")
public class Address {
	@XMLField("@Type")
	public String type;
	@XMLField("Name")
	public String name;
	@XMLField("//Street")
	public String street;
	@XMLField("City")
	public String city;
	@XMLField("//State")
	public String state;
	@XMLField("Zip")
	public int zip;
	@XMLField("/PurchaseOrder/Address/Country")
	public String country;
}