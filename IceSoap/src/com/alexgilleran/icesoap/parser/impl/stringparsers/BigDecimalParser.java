package com.alexgilleran.icesoap.parser.impl.stringparsers;

import java.math.BigDecimal;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.exception.XMLParsingException;

public class BigDecimalParser implements StringParser<BigDecimal> {

	@Override
	public BigDecimal parse(String stringValue, XMLField xmlField) throws XMLParsingException {
		return new BigDecimal(stringValue);
	}

}
