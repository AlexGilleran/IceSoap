package com.alexgilleran.icesoap.parser.impl.stringparsers;

import com.alexgilleran.icesoap.annotation.XMLField;

public class DoubleParser implements StringParser<Double> {

	@Override
	public Double parse(String stringValue, XMLField xmlField) {
		return Double.parseDouble(stringValue);
	}

}
