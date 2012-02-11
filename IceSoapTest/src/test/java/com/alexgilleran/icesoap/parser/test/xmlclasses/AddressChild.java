package com.alexgilleran.icesoap.parser.test.xmlclasses;

import com.alexgilleran.icesoap.annotation.XMLField;

public class AddressChild extends AddressParent {
	@XMLField("//State")
	private String state;
	@XMLField("Zip")
	private int zip;
	@XMLField("/PurchaseOrder/Address/Country")
	private String country;

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
