package com.alexgilleran.icesoap.example.domain;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

// XML Object being parsed:
//<Dictionary>
//<Id>devils</Id>
//<Name>THE DEVIL'S DICTIONARY ((C)1911 Released April 15 1993)</Name>
//</Dictionary>

/**
 * A dictionary
 */
@XMLObject("//Dictionary")
public class Dictionary {
	/** The ID of the dictionary as defined by the web service */
	@XMLField("Id")
	private String id;
	/** The name of the dictionary */
	@XMLField("Name")
	private String name;

	/**
	 * 
	 * @return the ID of the dictionary as defined by the web service
	 */
	public String getId() {
		return id;
	}

	/**
	 * 
	 * @return the name of the dictionary
	 */
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
}
