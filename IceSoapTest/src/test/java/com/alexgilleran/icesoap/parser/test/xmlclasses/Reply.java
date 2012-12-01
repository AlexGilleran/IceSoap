package com.alexgilleran.icesoap.parser.test.xmlclasses;

import java.util.Date;
import java.util.List;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

@XMLObject("//Reply")
public class Reply {

	@XMLField(value = "ReqTime", dateFormat = "yyyy-MM-dd'T'hh:mm:ss.sss")
	public Date reqTime;

	@XMLField("Zones")
	public List<Zone> zones;

	@XMLField("Users")
	public List<User> users;

	@XMLField("ExitTO")
	public int exitTo;
}
