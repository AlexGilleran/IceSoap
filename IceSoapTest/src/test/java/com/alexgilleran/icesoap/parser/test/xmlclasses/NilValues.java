package com.alexgilleran.icesoap.parser.test.xmlclasses;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

@XMLObject("//NilValues")
public class NilValues {
	@XMLField("char")
	char charValue;

	@XMLField("int")
	int intValue;

	@XMLField("long")
	long longValue;

	@XMLField("float")
	float floatValue;

	@XMLField("double")
	double doubleValue;

	@XMLField("boolean")
	boolean booleanValue;

	@XMLField("String")
	String stringValue;

	public char getCharValue() {
		return charValue;
	}

	public int getIntValue() {
		return intValue;
	}

	public long getLongValue() {
		return longValue;
	}

	public float getFloatValue() {
		return floatValue;
	}

	public double getDoubleValue() {
		return doubleValue;
	}

	public boolean isBooleanValue() {
		return booleanValue;
	}

	public String getStringValue() {
		return stringValue;
	}

}
