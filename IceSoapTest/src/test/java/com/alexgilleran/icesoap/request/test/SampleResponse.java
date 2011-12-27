package com.alexgilleran.icesoap.request.test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class SampleResponse {
	private final static String SINGLE_RESPONSE = "<Response>"//
			+ "<Details id=1>"//
			+ "<TextField>Text</TextField>"//
			+ "</Details>"//
			+ "</Response>";

	public static InputStream getSingleResponse() {
		return new ByteArrayInputStream(SINGLE_RESPONSE.getBytes());
	}
}
