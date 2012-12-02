package com.alexgilleran.icesoap.parser.test.xmlclasses;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

@XMLObject
public class NonAnnotatedObject {
	@XMLField("@Name")
	private String name;
	
	@XMLField("//id")
	private int id;

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}
}
