package com.alexgilleran.icesoap.parser.test.xmlclasses;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

@XMLObject("//NilValues")
public class PrimitiveObjects {

	@XMLField("char")
	Character charValue;

	@XMLField("int")
	Integer intValue;

	@XMLField("long")
	Long longValue;

	@XMLField("float")
	Float floatValue;

	@XMLField("double")
	Double doubleValue;

	@XMLField("boolean")
	Boolean booleanValue;

	public Character getCharValue() {
		return charValue;
	}

	public Integer getIntValue() {
		return intValue;
	}

	public Long getLongValue() {
		return longValue;
	}

	public Float getFloatValue() {
		return floatValue;
	}

	public Double getDoubleValue() {
		return doubleValue;
	}

	public Boolean getBooleanValue() {
		return booleanValue;
	}

}
