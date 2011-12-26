package com.alexgilleran.icesoap.parser.test;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Test;
import org.xmlpull.v1.XmlPullParserException;

import com.alexgilleran.icesoap.exception.XmlParsingException;
import com.alexgilleran.icesoap.parser.IceSoapListParser;
import com.alexgilleran.icesoap.parser.IceSoapParser;
import com.alexgilleran.icesoap.parser.ItemObserver;
import com.alexgilleran.icesoap.parser.impl.IceSoapListParserImpl;
import com.alexgilleran.icesoap.parser.impl.IceSoapParserImpl;
import com.alexgilleran.icesoap.parser.test.xmlclasses.Customer;
import com.alexgilleran.icesoap.parser.test.xmlclasses.CustsAndOrders;
import com.alexgilleran.icesoap.parser.test.xmlclasses.Order;

public class IceSoapListParserTest {
	private final static SimpleDateFormat FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd");

	/**
	 * Holistic test on realistic data.
	 * 
	 * @throws XmlPullParserException
	 * @throws XmlParsingException
	 * @throws ParseException
	 */
	@Test
	public void testCustomerList() throws XmlPullParserException,
			XmlParsingException, ParseException {
		CustomerObserver customerObserver = new CustomerObserver();
		IceSoapListParser<Customer> parser = new IceSoapListParserImpl<Customer>(
				Customer.class);
		parser.registerItemObserver(customerObserver);

		// Get customers
		List<Customer> custList = parser.parse(SampleXml
				.getCustomersAndOrders());

		assertEquals(4, customerObserver.counter);
		checkCustomerList(custList);
	}

	@Test
	public void testOrderList() throws XmlParsingException, ParseException {
		OrderObserver orderObserver = new OrderObserver();
		IceSoapListParser<Order> parser = new IceSoapListParserImpl<Order>(
				Order.class);
		parser.registerItemObserver(orderObserver);

		// Get orders
		List<Order> purchaseOrderList = parser.parse(SampleXml
				.getCustomersAndOrders());

		assertEquals(11, orderObserver.counter);
		checkOrderList(purchaseOrderList);
	}

	@Test
	public void testListsInTypes() throws XmlParsingException, ParseException {
		IceSoapParser<CustsAndOrders> parser = new IceSoapParserImpl<CustsAndOrders>(
				CustsAndOrders.class);

		// Get customers and orders
		CustsAndOrders custsAndOrders = parser.parse(SampleXml
				.getCustomersAndOrders());

		checkCustomerList(custsAndOrders.getCustomers());
		checkOrderList(custsAndOrders.getOrders());
	}

	private void checkOrderList(List<Order> purchaseOrders)
			throws ParseException {
		OrderObserver observer = new OrderObserver();

		// Check the numbers
		assertEquals(11, purchaseOrders.size());

		// Check the first and last are correct
		checkOrder(purchaseOrders.get(0), 0);
		checkOrder(purchaseOrders.get(10), 10);
	}

	private void checkOrder(Order order, int index) throws ParseException {
		switch (index) {
		case 0:
			// "<Order>"
			// + "<CustomerID>GREAL</CustomerID>"
			// + "<EmployeeID>6</EmployeeID>"
			// + "<OrderDate>1997-05-06T00:00:00</OrderDate>"
			// + "<RequiredDate>1997-05-20T00:00:00</RequiredDate>"
			// + "<ShipInfo ShippedDate=\"1997-05-09T00:00:00\">"
			// + "<ShipVia>2</ShipVia>"
			// + "<Freight>3.35</Freight>"
			// + "<ShipName>Great Lakes Food Market</ShipName>"
			// + "<ShipAddress>2732 Baker Blvd.</ShipAddress>"
			// + "<ShipCity>Eugene</ShipCity>"
			// + "<ShipRegion>OR</ShipRegion>"
			// + "<ShipPostalCode>97403</ShipPostalCode>"
			// + "<ShipCountry>USA</ShipCountry>"
			// + "</ShipInfo>"
			// + "</Order>"

			assertEquals("GREAL", order.getCustomerId());
			assertEquals(6, order.getEmployeeId());
			assertEquals(FORMAT.parse("1997-05-06"), order.getOrderDate());
			assertEquals(FORMAT.parse("1997-05-20"), order.getRequiredDate());
			assertEquals(FORMAT.parse("1997-05-09"), order.getShipInfo()
					.getShippedDate());
			assertEquals(2, order.getShipInfo().getShipVia());
			assertEquals(3.35d, order.getShipInfo().getFreight(), 0);
			assertEquals("Great Lakes Food Market", order.getShipInfo()
					.getShipName());
			assertEquals("2732 Baker Blvd.", order.getShipInfo()
					.getShipAddress());
			assertEquals("Eugene", order.getShipInfo().getShipCity());
			assertEquals("OR", order.getShipInfo().getShipRegion());
			assertEquals("97403", order.getShipInfo().getShipPostalCode());
			assertEquals("USA", order.getShipInfo().getShipCountry());
			break;
		case 10:
			// + "<Order>"
			// + "<CustomerID>GREAL</CustomerID>"
			// + "<EmployeeID>4</EmployeeID>"
			// + "<OrderDate>1998-04-30T00:00:00</OrderDate>"
			// + "<RequiredDate>1998-06-11T00:00:00</RequiredDate>"
			// + "<ShipInfo>"
			// + "<ShipVia>3</ShipVia>"
			// + "<Freight>14.01</Freight>"
			// + "<ShipName>Great Lakes Food Market</ShipName>"
			// + "<ShipAddress>2732 Baker Blvd.</ShipAddress>"
			// + "<ShipCity>Eugene</ShipCity>"
			// + "<ShipRegion>OR</ShipRegion>"
			// + "<ShipPostalCode>97403</ShipPostalCode>"
			// + "<ShipCountry>USA</ShipCountry>"
			// + "</ShipInfo>"
			// + "</Order>"

			assertEquals("GREAL", order.getCustomerId());
			assertEquals(4, order.getEmployeeId());
			assertEquals(FORMAT.parse("1998-04-30"), order.getOrderDate());
			assertEquals(FORMAT.parse("1998-06-11"), order.getRequiredDate());
			assertEquals(3, order.getShipInfo().getShipVia());
			assertEquals(14.01, order.getShipInfo().getFreight(), 0);
			assertEquals("Great Lakes Food Market", order.getShipInfo()
					.getShipName());
			assertEquals("2732 Baker Blvd.", order.getShipInfo()
					.getShipAddress());
			assertEquals("Eugene", order.getShipInfo().getShipCity());
			assertEquals("OR", order.getShipInfo().getShipRegion());
			assertEquals("97403", order.getShipInfo().getShipPostalCode());
			assertEquals("USA", order.getShipInfo().getShipCountry());
			break;
		}
	}

	private void checkCustomerList(List<Customer> custList) {
		// Ensure the number retrieved is correct
		assertEquals(4, custList.size());

		// Check the first and third in detail
		checkCustomer(custList.get(0), 0);
		checkCustomer(custList.get(2), 2);
	}

	private void checkCustomer(Customer customer, int index) {
		switch (index) {
		case 0:
			// "<Customer CustomerID=\"GREAL\">"
			// <CompanyName>Great Lakes Food Market</CompanyName>"
			// <ContactName>Howard Snyder</ContactName>"
			// <ContactTitle>Marketing Manager</ContactTitle>"
			// <Phone>(503) 555-7555</Phone>"
			// <FullAddress>"
			// <Address>2732 Baker Blvd.</Address>"
			// <City>Eugene</City>"
			// <Region>OR</Region>"
			// <PostalCode>97403</PostalCode>"
			// <Country>USA</Country>"
			// </FullAddress>"
			// </Customer>"

			assertEquals("GREAL", customer.getCustomerId());
			assertEquals("Great Lakes Food Market", customer.getCompanyName());
			assertEquals("Howard Snyder", customer.getContactName());
			assertEquals("Marketing Manager", customer.getContactTitle());
			assertEquals("(503) 555-7555", customer.getPhone());
			assertEquals("USA", customer.getFullAddress().getCountry());
			assertEquals("2732 Baker Blvd.", customer.getFullAddress()
					.getAddress());
			assertEquals("Eugene", customer.getFullAddress().getCity());
			assertEquals("97403", customer.getFullAddress().getPostalCode());
			assertEquals("OR", customer.getFullAddress().getRegion());
			break;
		case 2:
			// "<Customer CustomerID=\"LAZYK\">"
			// + "<CompanyName>Lazy K Kountry Store</CompanyName>"
			// + "<ContactName>John Steel</ContactName>"
			// + "<ContactTitle>Marketing Manager</ContactTitle>"
			// + "<Phone>(509) 555-7969</Phone>"
			// + "<Fax>(509) 555-6221</Fax>"
			// + "<FullAddress>"
			// + "<Address>12 Orchestra Terrace</Address>"
			// + "<City>Walla Walla</City>"
			// + "<Region>WA</Region>"
			// + "<PostalCode>99362</PostalCode>"
			// + "<Country>USA</Country>"
			// + "</FullAddress>"
			// + "</Customer>" assertEquals("GREAL",
			// customer.getCustomerId());

			assertEquals("LAZYK", customer.getCustomerId());
			assertEquals("Lazy K Kountry Store", customer.getCompanyName());
			assertEquals("John Steel", customer.getContactName());
			assertEquals("Marketing Manager", customer.getContactTitle());
			assertEquals("(509) 555-7969", customer.getPhone());
			assertEquals("12 Orchestra Terrace", customer.getFullAddress()
					.getAddress());
			assertEquals("Walla Walla", customer.getFullAddress().getCity());
			assertEquals("99362", customer.getFullAddress().getPostalCode());
			assertEquals("WA", customer.getFullAddress().getRegion());
			assertEquals("USA", customer.getFullAddress().getCountry());
			break;
		}
	}

	private class OrderObserver implements ItemObserver<Order> {
		int counter = 0;

		@Override
		public void onNewItem(Order item) {
			try {
				checkOrder(item, counter);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
			counter++;
		}
	}

	private class CustomerObserver implements ItemObserver<Customer> {
		int counter = 0;

		@Override
		public void onNewItem(Customer item) {
			checkCustomer(item, counter);
			counter++;
		}
	}
}
