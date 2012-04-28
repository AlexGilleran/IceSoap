package com.alexgilleran.icesoap.parser.test.xmlclasses;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;
//"<?xml version=\"1.0\"?>"
//+ "<Booleans attribute=\"true\">"
//+ "<FalseBoolean>false</FalseBoolean>"
//+ "<TrueBoolean>true</TrueBoolean>"
//+ "<UpperCaseBoolean>TRUE</UpperCaseBoolean>"
//+ "<TitleCaseBoolean>False</TitleCaseBoolean>"
//+ "</Booleans>";

@XMLObject("//Booleans")
public class Booleans {
	@XMLField("@attribute")
	private boolean attribute;

	@XMLField("FalseBoolean")
	private boolean falseBoolean;

	@XMLField("TrueBoolean")
	private boolean trueBoolean;

	@XMLField("UpperCaseBoolean")
	private boolean upperCaseBoolean;

	@XMLField("TitleCaseBoolean")
	private boolean titleCaseBoolean;

	public boolean isAttribute() {
		return attribute;
	}

	public boolean isFalseBoolean() {
		return falseBoolean;
	}

	public boolean isTrueBoolean() {
		return trueBoolean;
	}

	public boolean isUpperCaseBoolean() {
		return upperCaseBoolean;
	}

	public boolean isTitleCaseBoolean() {
		return titleCaseBoolean;
	}
}
