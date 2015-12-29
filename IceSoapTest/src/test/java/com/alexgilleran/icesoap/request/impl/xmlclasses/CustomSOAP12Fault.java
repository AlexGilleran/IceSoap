package com.alexgilleran.icesoap.request.impl.xmlclasses;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.soapfault.SOAP12Fault;

public class CustomSOAP12Fault extends SOAP12Fault {
	@XMLField("Detail/SqlMessage/Class")
	private int clazz;
	@XMLField("Detail/SqlMessage/LineNumber")
	private int lineNumber;
	@XMLField("Detail/SqlMessage/Message")
	private String message;
	@XMLField("Detail/SqlMessage/Number")
	private int number;
	@XMLField("Detail/SqlMessage/Source")
	private String source;
	@XMLField("Detail/SqlMessage/State")
	private int state;

	public int getClazz() {
		return clazz;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public String getMessage() {
		return message;
	}

	public int getNumber() {
		return number;
	}

	public String getSource() {
		return source;
	}

	public int getState() {
		return state;
	}
}
