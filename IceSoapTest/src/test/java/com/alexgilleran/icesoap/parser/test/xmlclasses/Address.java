/**
 * 
 */
package com.alexgilleran.icesoap.parser.test.xmlclasses;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

@XMLObject("//Address")
public class Address {
	@XMLField("@Type")
	private String type;
	@XMLField("Name")
	private String name;
	@XMLField("//Street")
	private String street;
	@XMLField("City")
	private String city;
	@XMLField("//State")
	private String state;
	@XMLField("Zip")
	private int zip;
	@XMLField("/PurchaseOrder/Address/Country")
	private String country;

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public int getZip() {
		return zip;
	}

	public String getCountry() {
		return country;
	}
}