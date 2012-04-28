package com.alexgilleran.icesoap.parser.test.xmlclasses;

import java.util.List;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

@XMLObject("//InvalidObjectList")
public class InvalidlyAnnotatedObjectList {
	@XMLField
	private List<InvalidlyAnnotatedObject> object;
}
