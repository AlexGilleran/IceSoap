package com.alexgilleran.icesoap.parser.impl.stringparsers;

import com.alexgilleran.icesoap.annotation.XMLField;

public class CharacterParser implements StringParser<Character> {

	@Override
	public Character parse(String stringValue, XMLField xmlField) {
		return new Character(stringValue.charAt(0));
	}

}
