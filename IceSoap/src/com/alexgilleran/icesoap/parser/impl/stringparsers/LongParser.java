package com.alexgilleran.icesoap.parser.impl.stringparsers;

import com.alexgilleran.icesoap.annotation.XMLField;

public class LongParser implements StringParser<Long> {

	@Override
	public Long parse(String stringValue, XMLField xmlField) {
		return Long.parseLong(stringValue);
	}

}
