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
	public String partNumber;
	@XMLField("ProductName")
	public String productName;
	@XMLField("Quantity")
	public double quantity;
	@XMLField("USPrice")
	public BigDecimal usPrice;
	@XMLField(value = "ShipDate", dateFormat = "yyyy-MM-dd")
	public Date shipDate;
	@XMLField("Comment")
	public String comment;
}
