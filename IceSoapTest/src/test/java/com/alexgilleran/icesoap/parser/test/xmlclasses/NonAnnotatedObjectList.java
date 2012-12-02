package com.alexgilleran.icesoap.parser.test.xmlclasses;

import java.util.List;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

@XMLObject("//NonAnnotatedObject")
public class NonAnnotatedObjectList {
	@XMLField("Object")
	private List<NonAnnotatedObject> object;

	public List<NonAnnotatedObject> getObjects() {
		return object;
	}
}
