/**
 * 
 */
package com.alexgilleran.icesoap.parser.test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @author Alex Gilleran
 * 
 */
public class SampleXml {
	private final static String CRAPPY_LIST = 
	  "<Reply>"
	+ "<ReqTime>2012-11-29T15:22:17.927</ReqTime>"
	+ "<Zones>"
	+ 	"<ID>1</ID>"
	+ "</Zones>"
	+ "<Zones>"
	+ 	"<ID>2</ID>"
	+ "</Zones>"
	+ "<Users>"
	+ 	"<Name>USER_1</Name>"
	+ "</Users>"
	+ "<Users>"
	+ 	"<Name>USER_2</Name>"
	+ "</Users>"
	+ "<ExitTO>0</ExitTO>"
	+ "</Reply>";
	
	private final static String NIL_VALUES = "<?xml version=\"1.0\"?>"
			+ "<NilValues xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
				+ "<char xsi:nil=\"true\" />"
				+ "<int xsi:nil=\"true\" />"
				+ "<long xsi:nil=\"true\" />"
				+ "<float xsi:nil=\"true\" />"
				+ "<double xsi:nil=\"true\" />"
				+ "<boolean xsi:nil=\"true\" />"
				+ "<String xsi:nil=\"true\" />"
			+ "</NilValues>";
	
	public final static int TYPE_CONVERSION_VALUE = 3;
	public final static String CSV_CONVERSION_VALUE_1 = "alpha";
	public final static String CSV_CONVERSION_VALUE_2 = "beta";
	public final static String CSV_CONVERSION_VALUE_3 = "omega";
	
	private final static String PROCESSOR_TEST = "<?xml version=\"1.0\"?>"
			+ "<ProcessorTest>"
			+ "<TypeConversionTest>" + TYPE_CONVERSION_VALUE + "</TypeConversionTest>" 
			+ "<CSVConversionTest>" + CSV_CONVERSION_VALUE_1 + "," + CSV_CONVERSION_VALUE_2 + "," + CSV_CONVERSION_VALUE_3 + "</CSVConversionTest>"
			+ "</ProcessorTest>";
	
	private final static String ADDRESS_LIST = "<?xml version=\"1.0\"?>"
			+ "<InvalidObjectList>" + "<Object />" + "<Object />"
			+ "<Object />" + "</InvalidObjectList>";

	private final static String BOOLEAN_VALUES = "<?xml version=\"1.0\"?>"
			+ "<Booleans attribute=\"true\">"
			+ "<FalseBoolean>false</FalseBoolean>"
			+ "<TrueBoolean>true</TrueBoolean>"
			+ "<UpperCaseBoolean>TRUE</UpperCaseBoolean>"
			+ "<TitleCaseBoolean>False</TitleCaseBoolean>" + "</Booleans>";

	private final static String PURCHASE_ORDER = "<?xml version=\"1.0\"?>"
			+ "<aw:PurchaseOrder"
			+ " aw:PurchaseOrderNumber=\"99503\""
			+ " aw:OrderDate=\"1999-10-20\""
			+ " xmlns:aw=\"http:www.adventure-works.com\">"
			+ "<aw:Address aw:Type=\"Shipping\">"
			+ "<aw:Name>Ellen Adams</aw:Name>"
			+ "<aw:Street>123 Maple Street</aw:Street>"
			+ "<aw:City>Mill Valley</aw:City>"
			+ "<aw:State>CA</aw:State>"
			+ "<aw:Zip>10999</aw:Zip>"
			+ "<aw:Country>USA</aw:Country>"
			+ "</aw:Address>"
			+ "<aw:Address aw:Type=\"Billing\">"
			+ "<aw:Name>Tai Yee</aw:Name>"
			+ "<aw:Street>8 Oak Avenue</aw:Street>"
			+ "<aw:City>Old Town</aw:City>"
			+ "<aw:State>PA</aw:State>"
			+ "<aw:Zip>95819</aw:Zip>"
			+ "<aw:Country>USA</aw:Country>"
			+ "</aw:Address>"
			+ "<aw:DeliveryNotes>Please leave packages in shed by driveway.</aw:DeliveryNotes>"
			+ "<aw:Items>" + "<aw:Item aw:PartNumber=\"872-AA\">"
			+ "<aw:ProductName>Lawnmower</aw:ProductName>"
			+ "<aw:Quantity>1</aw:Quantity>"
			+ "<aw:USPrice>148.95</aw:USPrice>"
			+ "<aw:Comment>Confirm this is electric</aw:Comment>"
			+ "</aw:Item>" + "<aw:Item aw:PartNumber=\"926-AA\">"
			+ "<aw:ProductName>Baby Monitor</aw:ProductName>"
			+ "<aw:Quantity>2</aw:Quantity>" + "<aw:USPrice>39.98</aw:USPrice>"
			+ "<aw:ShipDate>1999-05-21</aw:ShipDate>" + "</aw:Item>"
			+ "</aw:Items>" + "</aw:PurchaseOrder>";

	private static final String CUSTS_AND_ORDERS = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
			+ "<Root xmlns=\"http://www.adventure-works.com\"  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" 
			+ "<Customers>"
			+ "<Customer CustomerID=\"GREAL\">"
			+ "<CompanyName>Great Lakes Food Market</CompanyName>"
			+ "<ContactName>Howard Snyder</ContactName>"
			+ "<ContactTitle>Marketing Manager</ContactTitle>"
			+ "<Phone>(503) 555-7555</Phone>"
			+ "<FullAddress>"
			+ "<Address>2732 Baker Blvd.</Address>"
			+ "<City>Eugene</City>"
			+ "<Region>OR</Region>"
			+ "<PostalCode>97403</PostalCode>"
			+ "<Country>USA</Country>"
			+ "</FullAddress>"
			+ "</Customer>"
			+ "<Customer CustomerID=\"HUNGC\">"
			+ "<CompanyName>Hungry Coyote Import Store</CompanyName>"
			+ "<ContactName>Yoshi Latimer</ContactName>"
			+ "<ContactTitle>Sales Representative</ContactTitle>"
			+ "<Phone>(503) 555-6874</Phone>"
			+ "<Fax>(503) 555-2376</Fax>"
			+ "<FullAddress>"
			+ "<Address>City Center Plaza 516 Main St.</Address>"
			+ "<City>Elgin</City>"
			+ "<Region>OR</Region>"
			+ "<PostalCode>97827</PostalCode>"
			+ "<Country>USA</Country>"
			+ "</FullAddress>"
			+ "</Customer>"
			+ "<Customer CustomerID=\"LAZYK\">"
			+ "<CompanyName>Lazy K Kountry Store</CompanyName>"
			+ "<ContactName>John Steel</ContactName>"
			+ "<ContactTitle>Marketing Manager</ContactTitle>"
			+ "<Phone>(509) 555-7969</Phone>"
			+ "<Fax>(509) 555-6221</Fax>"
			+ "<FullAddress>"
			+ "<Address>12 Orchestra Terrace</Address>"
			+ "<City>Walla Walla</City>"
			+ "<Region>WA</Region>"
			+ "<PostalCode>99362</PostalCode>"
			+ "<Country>USA</Country>"
			+ "</FullAddress>"
			+ "</Customer>"
			+ "<Customer CustomerID=\"LETSS\">"
			+ "<CompanyName>Let's Stop N Shop</CompanyName>"
			+ "<ContactName>Jaime Yorres</ContactName>"
			+ "<ContactTitle>Owner</ContactTitle>"
			+ "<Phone>(415) 555-5938</Phone>"
			+ "<FullAddress>"
			+ "<Address>87 Polk St. Suite 5</Address>"
			+ "<City>San Francisco</City>"
			+ "<Region>CA</Region>"
			+ "<PostalCode>94117</PostalCode>"
			+ "<Country>USA</Country>"
			+ "</FullAddress>"
			+ "</Customer>"
			+ "</Customers>"
			+ "<Orders>"
			+ "<Order>"
			+ "<CustomerID>GREAL</CustomerID>"
			+ "<EmployeeID>6</EmployeeID>"
			+ "<OrderDate>1997-05-06T00:00:00</OrderDate>"
			+ "<RequiredDate>1997-05-20T00:00:00</RequiredDate>"
			+ "<ShipInfo ShippedDate=\"1997-05-09T00:00:00\">"
			+ "<ShipVia>2</ShipVia>"
			+ "<Freight>3.35</Freight>"
			+ "<ShipName>Great Lakes Food Market</ShipName>"
			+ "<ShipAddress>2732 Baker Blvd.</ShipAddress>"
			+ "<ShipCity>Eugene</ShipCity>"
			+ "<ShipRegion>OR</ShipRegion>"
			+ "<ShipPostalCode>97403</ShipPostalCode>"
			+ "<ShipCountry>USA</ShipCountry>"
			+ "</ShipInfo>"
			+ "</Order>"
			+ "<Order>"
			+ "<CustomerID>GREAL</CustomerID>"
			+ "<EmployeeID>8</EmployeeID>"
			+ "<OrderDate>1997-07-04T00:00:00</OrderDate>"
			+ "<RequiredDate>1997-08-01T00:00:00</RequiredDate>"
			+ "<ShipInfo ShippedDate=\"1997-07-14T00:00:00\">"
			+ "<ShipVia>2</ShipVia>"
			+ "<Freight>4.42</Freight>"
			+ "<ShipName>Great Lakes Food Market</ShipName>"
			+ "<ShipAddress>2732 Baker Blvd.</ShipAddress>"
			+ "<ShipCity>Eugene</ShipCity>"
			+ "<ShipRegion>OR</ShipRegion>"
			+ "<ShipPostalCode>97403</ShipPostalCode>"
			+ "<ShipCountry>USA</ShipCountry>"
			+ "</ShipInfo>"
			+ "</Order>"
			+ "<Order>"
			+ "<CustomerID>GREAL</CustomerID>"
			+ "<EmployeeID>1</EmployeeID>"
			+ "<OrderDate>1997-07-31T00:00:00</OrderDate>"
			+ "<RequiredDate>1997-08-28T00:00:00</RequiredDate>"
			+ "<ShipInfo ShippedDate=\"1997-08-05T00:00:00\">"
			+ "<ShipVia>2</ShipVia>"
			+ "<Freight>116.53</Freight>"
			+ "<ShipName>Great Lakes Food Market</ShipName>"
			+ "<ShipAddress>2732 Baker Blvd.</ShipAddress>"
			+ "<ShipCity>Eugene</ShipCity>"
			+ "<ShipRegion>OR</ShipRegion>"
			+ "<ShipPostalCode>97403</ShipPostalCode>"
			+ "<ShipCountry>USA</ShipCountry>"
			+ "</ShipInfo>"
			+ "</Order>"
			+ "<Order>"
			+ "<CustomerID>GREAL</CustomerID>"
			+ "<EmployeeID>4</EmployeeID>"
			+ "<OrderDate>1997-07-31T00:00:00</OrderDate>"
			+ "<RequiredDate>1997-08-28T00:00:00</RequiredDate>"
			+ "<ShipInfo ShippedDate=\"1997-08-04T00:00:00\">"
			+ "<ShipVia>2</ShipVia>"
			+ "<Freight>18.53</Freight>"
			+ "<ShipName>Great Lakes Food Market</ShipName>"
			+ "<ShipAddress>2732 Baker Blvd.</ShipAddress>"
			+ "<ShipCity>Eugene</ShipCity>"
			+ "<ShipRegion>OR</ShipRegion>"
			+ "<ShipPostalCode>97403</ShipPostalCode>"
			+ "<ShipCountry>USA</ShipCountry>"
			+ "</ShipInfo>"
			+ "</Order>"
			+ "<Order>"
			+ "<CustomerID>GREAL</CustomerID>"
			+ "<EmployeeID>6</EmployeeID>"
			+ "<OrderDate>1997-09-04T00:00:00</OrderDate>"
			+ "<RequiredDate>1997-10-02T00:00:00</RequiredDate>"
			+ "<ShipInfo ShippedDate=\"1997-09-10T00:00:00\">"
			+ "<ShipVia>1</ShipVia>"
			+ "<Freight>57.15</Freight>"
			+ "<ShipName>Great Lakes Food Market</ShipName>"
			+ "<ShipAddress>2732 Baker Blvd.</ShipAddress>"
			+ "<ShipCity>Eugene</ShipCity>"
			+ "<ShipRegion>OR</ShipRegion>"
			+ "<ShipPostalCode>97403</ShipPostalCode>"
			+ "<ShipCountry>USA</ShipCountry>"
			+ "</ShipInfo>"
			+ "</Order>"
			+ "<Order>"
			+ "<CustomerID>GREAL</CustomerID>"
			+ "<EmployeeID>3</EmployeeID>"
			+ "<OrderDate>1997-09-25T00:00:00</OrderDate>"
			+ "<RequiredDate>1997-10-23T00:00:00</RequiredDate>"
			+ "<ShipInfo ShippedDate=\"1997-09-30T00:00:00\">"
			+ "<ShipVia>3</ShipVia>"
			+ "<Freight>76.13</Freight>"
			+ "<ShipName>Great Lakes Food Market</ShipName>"
			+ "<ShipAddress>2732 Baker Blvd.</ShipAddress>"
			+ "<ShipCity>Eugene</ShipCity>"
			+ "<ShipRegion>OR</ShipRegion>"
			+ "<ShipPostalCode>97403</ShipPostalCode>"
			+ "<ShipCountry>USA</ShipCountry>"
			+ "</ShipInfo>"
			+ "</Order>"
			+ "<Order>"
			+ "<CustomerID>GREAL</CustomerID>"
			+ "<EmployeeID>4</EmployeeID>"
			+ "<OrderDate>1998-01-06T00:00:00</OrderDate>"
			+ "<RequiredDate>1998-02-03T00:00:00</RequiredDate>"
			+ "<ShipInfo ShippedDate=\"1998-02-04T00:00:00\">"
			+ "<ShipVia>2</ShipVia>"
			+ "<Freight>719.78</Freight>"
			+ "<ShipName>Great Lakes Food Market</ShipName>"
			+ "<ShipAddress>2732 Baker Blvd.</ShipAddress>"
			+ "<ShipCity>Eugene</ShipCity>"
			+ "<ShipRegion>OR</ShipRegion>"
			+ "<ShipPostalCode>97403</ShipPostalCode>"
			+ "<ShipCountry>USA</ShipCountry>"
			+ "</ShipInfo>"
			+ "</Order>"
			+ "<Order>"
			+ "<CustomerID>GREAL</CustomerID>"
			+ "<EmployeeID>3</EmployeeID>"
			+ "<OrderDate>1998-03-09T00:00:00</OrderDate>"
			+ "<RequiredDate>1998-04-06T00:00:00</RequiredDate>"
			+ "<ShipInfo ShippedDate=\"1998-03-18T00:00:00\">"
			+ "<ShipVia>2</ShipVia>"
			+ "<Freight>33.68</Freight>"
			+ "<ShipName>Great Lakes Food Market</ShipName>"
			+ "<ShipAddress>2732 Baker Blvd.</ShipAddress>"
			+ "<ShipCity>Eugene</ShipCity>"
			+ "<ShipRegion>OR</ShipRegion>"
			+ "<ShipPostalCode>97403</ShipPostalCode>"
			+ "<ShipCountry>USA</ShipCountry>"
			+ "</ShipInfo>"
			+ "</Order>"
			+ "<Order>"
			+ "<CustomerID>GREAL</CustomerID>"
			+ "<EmployeeID>3</EmployeeID>"
			+ "<OrderDate>1998-04-07T00:00:00</OrderDate>"
			+ "<RequiredDate>1998-05-05T00:00:00</RequiredDate>"
			+ "<ShipInfo ShippedDate=\"1998-04-15T00:00:00\">"
			+ "<ShipVia>2</ShipVia>"
			+ "<Freight>25.19</Freight>"
			+ "<ShipName>Great Lakes Food Market</ShipName>"
			+ "<ShipAddress>2732 Baker Blvd.</ShipAddress>"
			+ "<ShipCity>Eugene</ShipCity>"
			+ "<ShipRegion>OR</ShipRegion>"
			+ "<ShipPostalCode>97403</ShipPostalCode>"
			+ "<ShipCountry>USA</ShipCountry>"
			+ "</ShipInfo>"
			+ "</Order>"
			+ "<Order>"
			+ "<CustomerID>GREAL</CustomerID>"
			+ "<EmployeeID>4</EmployeeID>"
			+ "<OrderDate>1998-04-22T00:00:00</OrderDate>"
			+ "<RequiredDate>1998-05-20T00:00:00</RequiredDate>"
			+ "<ShipInfo>"
			+ "<ShipVia>3</ShipVia>"
			+ "<Freight>18.84</Freight>"
			+ "<ShipName>Great Lakes Food Market</ShipName>"
			+ "<ShipAddress>2732 Baker Blvd.</ShipAddress>"
			+ "<ShipCity>Eugene</ShipCity>"
			+ "<ShipRegion>OR</ShipRegion>"
			+ "<ShipPostalCode>97403</ShipPostalCode>"
			+ "<ShipCountry>USA</ShipCountry>"
			+ "</ShipInfo>"
			+ "</Order>"
			+ "<Order xsi:nil=\"true\" />"  // NIL!
			+ "<Order>"
			+ "<CustomerID>GREAL</CustomerID>"
			+ "<EmployeeID>4</EmployeeID>"
			+ "<OrderDate>1998-04-30T00:00:00</OrderDate>"
			+ "<RequiredDate>1998-06-11T00:00:00</RequiredDate>"
			+ "<ShipInfo>"
			+ "<ShipVia>3</ShipVia>"
			+ "<Freight>14.01</Freight>"
			+ "<ShipName>Great Lakes Food Market</ShipName>"
			+ "<ShipAddress>2732 Baker Blvd.</ShipAddress>"
			+ "<ShipCity>Eugene</ShipCity>"
			+ "<ShipRegion>OR</ShipRegion>"
			+ "<ShipPostalCode>97403</ShipPostalCode>"
			+ "<ShipCountry>USA</ShipCountry>"
			+ "</ShipInfo>"
			+ "</Order>"
			+ "</Orders>" + "</Root>";

	public static final String SF_ATTR_1 = "ATTRIBUTE_1";
	public static final String SF_ATTR_2 = "ATTRIBUTE_2";
	public static final String SF_ATTR_3 = "ATTRIBUTE_3";
	public static final String SF_VALUE_1 = "VALUE_1";
	public static final String SF_VALUE_2 = "VALUE_2";
	public static final String SF_VALUE_3 = "VALUE_3";

	public static final String SINGLE_FIELD_WITH_ATTRIBUTE_XML = "<node>"//
			+ "<field attribute=\"" + SF_ATTR_1 + "\">"
			+ SF_VALUE_1
			+ "</field>" + "<field attribute=\"" + SF_ATTR_2
			+ "\">"
			+ SF_VALUE_2 + "</field>" + "<field attribute=\""
			+ SF_ATTR_3
			+ "\">" + SF_VALUE_3 + "</field>" + "</node>";

	public static final String SMS_ALERT_GROUP_1 = "Medical_Emergency";
	public static final String SMS_ALERT_GROUP_2 = "Peripherals";
	
	public static final String LIST_OF_STRINGS_XML =
		"<alerts xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +
		"<Alert>" + 
			"<Id>1</Id>" +
			"<Contact>Jonas</Contact>" +
			"<Email>some_email</Email>" +
			"<Phone>555-555555</Phone>" +
			"<ActiveGroupsPerEmail>" +
				"<AlertGroup>Fire</AlertGroup>" +
				"<AlertGroup xsi:nil=\"true\" />" + // NIL!
				"<AlertGroup>OpenClose</AlertGroup>" +
			"</ActiveGroupsPerEmail>" +
			"<ActiveGroupsPerSMS>" +
		        "<AlertGroup>" + SMS_ALERT_GROUP_1 + "</AlertGroup>" +
		        "<AlertGroup>" + SMS_ALERT_GROUP_2 + "</AlertGroup>" +
			"</ActiveGroupsPerSMS>" +
			"<LangId>en</LangId>" +
		"</Alert>" +
	"</alerts>";

	
	public static InputStream getSingleFieldsWithAttributes() {
		return new ByteArrayInputStream(
				SINGLE_FIELD_WITH_ATTRIBUTE_XML.getBytes());
	}

	public static InputStream getNilValues() {
		return new ByteArrayInputStream(NIL_VALUES.getBytes());
	}

	public static InputStream getPurchaseOrder() {
		return new ByteArrayInputStream(PURCHASE_ORDER.getBytes());
	}

	public static InputStream getCustomersAndOrders() {
		return new ByteArrayInputStream(CUSTS_AND_ORDERS.getBytes());
	}

	public static InputStream getInvalidList() {
		return new ByteArrayInputStream(ADDRESS_LIST.getBytes());
	}

	public static InputStream getBooleans() {
		return new ByteArrayInputStream(BOOLEAN_VALUES.getBytes());
	}

	public static InputStream getProcessorTest() {
		return new ByteArrayInputStream(PROCESSOR_TEST.getBytes());
	}

	public static InputStream getListOfStringsXML() {
		return new ByteArrayInputStream(LIST_OF_STRINGS_XML.getBytes());
	}
	
	public static InputStream getCrappyList() {
		return new ByteArrayInputStream(CRAPPY_LIST.getBytes());
	}
}
