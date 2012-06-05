package com.alexgilleran.icesoap.example.processors;

import com.alexgilleran.icesoap.parser.processor.Processor;

/**
 * The dictionary service by default gives us definitions with links in them
 * that are contained within curly braces ({}). Ultimately it'd be nice to
 * implement the links in the app, but for now we're just gonna cut them out
 * with String.replace().
 * 
 * @author Alex Gilleran
 * 
 */
public class DefinitionProcessor implements Processor<String> {

	@Override
	public String process(String inputValue) {
		inputValue = inputValue.replace("{", "");
		inputValue = inputValue.replace("}", "");

		return inputValue;
	}

}
