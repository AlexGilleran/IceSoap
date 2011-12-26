package com.alexgilleran.icesoap.parser.test.xmlclasses;

import java.util.Date;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

// + "<ShipInfo ShippedDate=\"1997-07-14T00:00:00\">"
// + "<ShipVia>2</ShipVia>"
// + "<Freight>4.42</Freight>"
// + "<ShipName>Great Lakes Food Market</ShipName>"
// + "<ShipAddress>2732 Baker Blvd.</ShipAddress>"
// + "<ShipCity>Eugene</ShipCity>"
// + "<ShipRegion>OR</ShipRegion>"
// + "<ShipPostalCode>97403</ShipPostalCode>"
// + "<ShipCountry>USA</ShipCountry>"
// + "</ShipInfo>"
@XMLObject("//ShipInfo")
public class ShipInfo {
	@XMLField(value = "@ShippedDate", dateFormat = "yyyy-MM-dd'T'hh:mm:ss")
	private Date shippedDate;
	@XMLField("ShipVia")
	private int shipVia;
	@XMLField("Freight")
	private double freight;
	@XMLField("ShipName")
	private String shipName;
	@XMLField("ShipAddress")
	private String shipAddress;
	@XMLField("ShipCity")
	private String shipCity;
	@XMLField("ShipRegion")
	private String shipRegion;
	@XMLField("ShipPostalCode")
	private String shipPostalCode;
	@XMLField("ShipCountry")
	private String shipCountry;

	public Date getShippedDate() {
		return shippedDate;
	}

	public int getShipVia() {
		return shipVia;
	}

	public double getFreight() {
		return freight;
	}

	public String getShipName() {
		return shipName;
	}

	public String getShipAddress() {
		return shipAddress;
	}

	public String getShipCity() {
		return shipCity;
	}

	public String getShipRegion() {
		return shipRegion;
	}

	public String getShipPostalCode() {
		return shipPostalCode;
	}

	public String getShipCountry() {
		return shipCountry;
	}
}
