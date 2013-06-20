package com.alexgilleran.icesoap.parser.impl.stringparsers;

import com.alexgilleran.icesoap.annotation.XMLField;

public class IntegerParser implements StringParser<Integer> {

	@Override
	public Integer parse(String stringValue, XMLField xmlField) {
		return Integer.parseInt(stringValue);
	}

}
