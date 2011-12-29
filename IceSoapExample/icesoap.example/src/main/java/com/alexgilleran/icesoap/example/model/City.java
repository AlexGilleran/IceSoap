package com.alexgilleran.icesoap.example.model;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

@XMLObject("//Table")
public class City {
	@XMLField("City")
	private String city;

	public String getCity() {
		return city;
	}

	@Override
	public String toString() {
		return city;
	}
}
