package com.alexgilleran.icesoap.soapfault;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

@XMLObject("//Reason/Text")
public class FaultReason {
	@XMLField("@lang")
	private String lang;

	@XMLField
	private String reason;

	public String getLang() {
		return lang;
	}

	public String getReason() {
		return reason;
	}
}
