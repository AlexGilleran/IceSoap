package com.alexgilleran.icesoap.parser.impl.stringparsers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.exception.XMLParsingException;

public class DateParser implements StringParser<Date> {

	@Override
	public Date parse(String valueString, XMLField xmlField) throws XMLParsingException {
		try {
			return new SimpleDateFormat(xmlField.dateFormat()).parse(valueString);
		} catch (ParseException e) {
			throw new XMLParsingException("Encountered date parsing exception when parsing " + valueString
					+ " with format " + xmlField.dateFormat() + " for value " + valueString, e);
		}
	}

}
