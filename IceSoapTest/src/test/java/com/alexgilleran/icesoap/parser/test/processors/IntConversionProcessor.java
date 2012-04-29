package com.alexgilleran.icesoap.parser.test.processors;

import com.alexgilleran.icesoap.parser.processor.Processor;

/**
 * Converts from an int to a string
 * 
 * @author Alex Gilleran
 * 
 */
public class IntConversionProcessor implements Processor<Integer> {

	@Override
	public Integer process(String inputValue) {
		return Integer.parseInt(inputValue);
	}

}
