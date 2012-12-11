package com.alexgilleran.icesoap.parser.test.xmlclasses;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

@XMLObject("//Object | /Pipe")
public class PipeTest {
	@XMLField("Value1 | Value2")
	private String value;

	public String getValue() {
		return value;
	}
}
