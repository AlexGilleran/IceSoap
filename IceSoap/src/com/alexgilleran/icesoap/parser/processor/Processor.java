package com.alexgilleran.icesoap.parser.processor;

/**
 * Allows for the custom processing of a single field value - specifies one
 * method {@link #process(String)}, in which input is taken in as a String
 * (straight from the SOAP Envelope), this information is processed by
 * implementation code, then the result is returned as an instance of the
 * generic OutputType
 * 
 * @author Alex Gilleran
 * 
 * @param <OutputType>
 *            The type to output (and hence set as a field).
 */
public interface Processor<OutputType> {
	/**
	 * Processes a raw value from a SOAPEnvelope (as a string) into the output
	 * value of the correct type.
	 * 
	 * @param inputValue
	 *            The value to process.
	 * @return The fully processed value.
	 */
	public OutputType process(String inputValue);
}
