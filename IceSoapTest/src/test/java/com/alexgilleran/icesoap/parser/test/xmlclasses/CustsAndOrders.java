package com.alexgilleran.icesoap.parser.test.xmlclasses;

import java.util.List;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

@XMLObject("/Root")
public class CustsAndOrders {
	@XMLField("/Root/Customers/Customer")
	private List<Customer> customers;
	@XMLField("Orders/Order")
	private List<Order> orders;

	public List<Customer> getCustomers() {
		return customers;
	}

	public List<Order> getOrders() {
		return orders;
	}
}
