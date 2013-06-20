package com.alexgilleran.icesoap.parser.impl.stringparsers;

import com.alexgilleran.icesoap.annotation.XMLField;

public class FloatParser implements StringParser<Float> {

	@Override
	public Float parse(String stringValue, XMLField xmlField) {
		return Float.parseFloat(stringValue);
	}

}
