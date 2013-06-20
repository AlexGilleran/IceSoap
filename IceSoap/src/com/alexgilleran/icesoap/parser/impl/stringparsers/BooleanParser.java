package com.alexgilleran.icesoap.parser.impl.stringparsers;

import com.alexgilleran.icesoap.annotation.XMLField;

public class BooleanParser implements StringParser<Boolean> {

	@Override
	public Boolean parse(String stringValue, XMLField xmlField) {
		return Boolean.parseBoolean(stringValue);
	}

}
