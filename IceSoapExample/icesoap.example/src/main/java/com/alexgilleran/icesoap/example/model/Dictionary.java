package com.alexgilleran.icesoap.example.model;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

//<Dictionary>
//<Id>devils</Id>
//<Name>THE DEVIL'S DICTIONARY ((C)1911 Released April 15 1993)</Name>
//</Dictionary>

@XMLObject("//Dictionary")
public class Dictionary {
	@XMLField("Id")
	private String id;
	@XMLField("Name")
	private String name;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
}
