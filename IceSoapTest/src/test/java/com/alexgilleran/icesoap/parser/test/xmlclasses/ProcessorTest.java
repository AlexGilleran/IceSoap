package com.alexgilleran.icesoap.parser.test.xmlclasses;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;
import com.alexgilleran.icesoap.parser.test.processors.CSVConversionProcessor;
import com.alexgilleran.icesoap.parser.test.processors.IntConversionProcessor;

@XMLObject("//ProcessorTest")
public class ProcessorTest {
	@XMLField(value = "TypeConversionTest", processor = IntConversionProcessor.class)
	private int conversionTest;

	@XMLField(value = "CSVConversionTest", processor = CSVConversionProcessor.class)
	private String[] csvTest;

	public ProcessorTest() {

	}

	public int getConversionTest() {
		return conversionTest;
	}

	public String[] getCsvTest() {
		return csvTest;
	}
}
