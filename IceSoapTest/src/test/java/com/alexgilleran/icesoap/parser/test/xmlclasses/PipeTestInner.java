/**
 * 
 */
package com.alexgilleran.icesoap.parser.test.xmlclasses;

import com.alexgilleran.icesoap.annotation.XMLField;

/**
 * @author Alex Gilleran
 * 
 */
public class PipeTestInner {
	@XMLField("InnerValue1 | //InnerValue11")
	private String innerValue1;
	@XMLField("InnerValue2 | //InnerValue22")
	private String innerValue2;

	public String getInnerValue1() {
		return innerValue1;
	}

	public String getInnerValue2() {
		return innerValue2;
	}
}
