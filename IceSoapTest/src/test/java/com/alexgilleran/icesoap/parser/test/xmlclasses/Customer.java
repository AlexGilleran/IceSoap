package com.alexgilleran.icesoap.parser.test.xmlclasses;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

//"<Customer CustomerID=\"GREAL\">"
//+ "<CompanyName>Great Lakes Food Market</CompanyName>"
//+ "<ContactName>Howard Snyder</ContactName>"
//+ "<ContactTitle>Marketing Manager</ContactTitle>"
//+ "<Phone>(503) 555-7555</Phone>"
//+ "<FullAddress>"
//+ "<Address>2732 Baker Blvd.</Address>"
//+ "<City>Eugene</City>"
//+ "<Region>OR</Region>"
//+ "<PostalCode>97403</PostalCode>"
//+ "<Country>USA</Country>"
//+ "</FullAddress>"

@XMLObject("//Customer")
public class Customer {
	@XMLField("@CustomerID")
	private String customerId;
	@XMLField("CompanyName")
	private String companyName;
	@XMLField("ContactName")
	private String contactName;
	@XMLField("ContactTitle")
	private String contactTitle;
	@XMLField("Phone")
	private String phone;
	@XMLField("FullAddress")
	private CustAddress fullAddress;

	public String getCustomerId() {
		return customerId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getContactName() {
		return contactName;
	}

	public String getContactTitle() {
		return contactTitle;
	}

	public String getPhone() {
		return phone;
	}

	public CustAddress getFullAddress() {
		return fullAddress;
	}
}
