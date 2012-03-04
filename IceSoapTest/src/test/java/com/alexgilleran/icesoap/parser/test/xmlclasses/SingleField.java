package com.alexgilleran.icesoap.parser.test.xmlclasses;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

@XMLObject("//field")
public class SingleField {
	@XMLField("@attribute")
	private String attribute;
	@XMLField
	private String value;

	public String getAttribute() {
		return attribute;
	}

	public String getValue() {
		return value;
	}
}
