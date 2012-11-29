package com.alexgilleran.icesoap.parser.test.xmlclasses;

import java.util.List;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

@XMLObject("//Alert")
public class Alert {
	@XMLField("Id")
	private int mId;

	@XMLField("Contact")
	private String mContact;

	@XMLField("Email")
	private String mEmail;

	@XMLField("Phone")
	private String mPhone;

	@XMLField("ActiveGroupsPerSMS/AlertGroup")
	private List<String> mActiveGroupsPerSMS;

	public List<String> getmActiveGroupsPerSMS() {
		return mActiveGroupsPerSMS;
	}
}