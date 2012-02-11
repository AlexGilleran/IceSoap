package com.alexgilleran.icesoap.parser.test.xmlclasses;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

@XMLObject("//Address")
public class AddressParent {
	@XMLField("@Type")
	private String type;
	@XMLField("Name")
	private String name;
	@XMLField("//Street")
	private String street;
	@XMLField("City")
	private String city;

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
}
