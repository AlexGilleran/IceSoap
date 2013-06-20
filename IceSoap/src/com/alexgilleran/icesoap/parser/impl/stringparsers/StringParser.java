package com.alexgilleran.icesoap.parser.impl.stringparsers;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.exception.XMLParsingException;

public interface StringParser<ParsedType extends Object> {
	ParsedType parse(String stringValue, XMLField xmlField) throws XMLParsingException;
}
