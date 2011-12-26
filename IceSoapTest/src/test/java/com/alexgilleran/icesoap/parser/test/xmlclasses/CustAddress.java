package com.alexgilleran.icesoap.parser.test.xmlclasses;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

//+ "<Address>87 Polk St. Suite 5</Address>"
//+ "<City>San Francisco</City>"
//+ "<Region>CA</Region>"
//+ "<PostalCode>94117</PostalCode>"
//+ "<Country>USA</Country>
@XMLObject
public class CustAddress {
	@XMLField("Address")
	private String address;
	@XMLField("City")
	private String city;
	@XMLField("Region")
	private String region;
	@XMLField("PostalCode")
	private String postalCode;
	@XMLField("Country")
	private String country;

	public String getAddress() {
		return address;
	}

	public String getCity() {
		return city;
	}

	public String getRegion() {
		return region;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getCountry() {
		return country;
	}
}
