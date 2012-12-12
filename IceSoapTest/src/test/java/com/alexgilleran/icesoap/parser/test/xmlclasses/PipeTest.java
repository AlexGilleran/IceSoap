package com.alexgilleran.icesoap.parser.test.xmlclasses;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

@XMLObject("//Object | /Pipe | /HergyBlergy")
public class PipeTest {
	@XMLField("Value1 | //Value2 | /HergyBlergy/Value3")
	private String value;

	@XMLField("//inner | testInner")
	private PipeTestInner inner;

	public String getValue() {
		return value;
	}

	public PipeTestInner getInner() {
		return inner;
	}
}
