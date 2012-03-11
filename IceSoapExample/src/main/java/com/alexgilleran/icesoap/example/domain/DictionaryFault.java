package com.alexgilleran.icesoap.example.domain;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.soapfault.SOAP11Fault;

public class DictionaryFault extends SOAP11Fault {
	@XMLField("detail/Error/ErrorMessage")
	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

}
