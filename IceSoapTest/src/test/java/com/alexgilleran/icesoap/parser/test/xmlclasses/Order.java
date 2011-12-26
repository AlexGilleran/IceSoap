package com.alexgilleran.icesoap.parser.test.xmlclasses;

import java.util.Date;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

@XMLObject("//Order")
public class Order {
	// "<CustomerID>GREAL</CustomerID>"
	// + "<EmployeeID>8</EmployeeID>"
	// + "<OrderDate>1997-07-04T00:00:00</OrderDate>"
	// + "<RequiredDate>1997-08-01T00:00:00</RequiredDate>"
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
	// + "</Order>"

	@XMLField("CustomerID")
	private String customerId;
	@XMLField("EmployeeID")
	private long employeeId;
	@XMLField(value = "OrderDate", dateFormat = "yyyy-MM-dd'T'hh:mm:ss")
	private Date orderDate;
	@XMLField(value = "RequiredDate", dateFormat = "yyyy-MM-dd'T'hh:mm:ss")
	private Date requiredDate;
	@XMLField("ShipInfo")
	private ShipInfo shipInfo;

	public String getCustomerId() {
		return customerId;
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public Date getRequiredDate() {
		return requiredDate;
	}

	public ShipInfo getShipInfo() {
		return shipInfo;
	}
}
