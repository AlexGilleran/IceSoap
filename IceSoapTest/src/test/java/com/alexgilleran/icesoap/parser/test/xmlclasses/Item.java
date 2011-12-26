/**
 * 
 */
package com.alexgilleran.icesoap.parser.test.xmlclasses;

import java.math.BigDecimal;
import java.util.Date;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

/**
 * @author Alex Gilleran
 * 
 */
@XMLObject
public class Item {
	@XMLField("@PartNumber")
	private String partNumber;
	@XMLField("ProductName")
	private String productName;
	@XMLField("Quantity")
	private double quantity;
	@XMLField("USPrice")
	private BigDecimal usPrice;
	@XMLField(value = "ShipDate", dateFormat = "yyyy-MM-dd")
	private Date shipDate;
	@XMLField("Comment")
	private String comment;

	public String getPartNumber() {
		return partNumber;
	}

	public String getProductName() {
		return productName;
	}

	public double getQuantity() {
		return quantity;
	}

	public BigDecimal getUsPrice() {
		return usPrice;
	}

	public Date getShipDate() {
		return shipDate;
	}

	public String getComment() {
		return comment;
	}
}
