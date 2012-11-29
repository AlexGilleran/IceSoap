package com.alexgilleran.icesoap.parser.test.xmlclasses;

import java.util.List;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

@XMLObject("//Alert")
public class Alert {
	@XMLField("Id")
	private int id;

	@XMLField("Contact")
	private String contact;

	@XMLField("Email")
	private String email;

	@XMLField("Phone")
	private String phone;

	@XMLField("ActiveGroupsPerEmail/AlertGroup")
	private List<String> activeGroupsPerEmail;

	@XMLField("ActiveGroupsPerSMS/AlertGroup")
	private List<String> activeGroupsPerSMS;

	public List<String> getActiveGroupsPerSMS() {
		return activeGroupsPerSMS;
	}

	public int getId() {
		return id;
	}

	public String getContact() {
		return contact;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public List<String> getActiveGroupsPerEmail() {
		return activeGroupsPerEmail;
	}
}