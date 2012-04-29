package com.alexgilleran.icesoap.parser.test.processors;

import com.alexgilleran.icesoap.parser.processor.Processor;

public class CSVConversionProcessor implements Processor<String[]> {

	@Override
	public String[] process(String inputValue) {
		return inputValue.split(",");
	}

}
