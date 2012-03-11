package com.alexgilleran.icesoap.example.dao;

import java.util.List;

import com.alexgilleran.icesoap.example.domain.Definition;
import com.alexgilleran.icesoap.example.domain.Dictionary;
import com.alexgilleran.icesoap.example.domain.DictionaryFault;
import com.alexgilleran.icesoap.request.Request;
import com.alexgilleran.icesoap.request.SOAP11ListRequest;
import com.alexgilleran.icesoap.request.SOAP11Request;

/**
 * Very simple factory to generate requests for the application.
 * 
 * Ideally you want to have something like this in order to make for easy
 * mocking - you can switch the implementation for a mock at runtime and unit
 * test without the need for an internet connection or a SOAPUI mock or
 * anything.
 * 
 * @author Alex Gilleran
 * 
 */
public interface DictionaryRequestFactory {
	/**
	 * Gets a list of all dictionaries available.
	 * 
	 * @return A {@link SOAP11Request} that will return all dictionaries
	 *         available, as a {@link List}, on execution.
	 */
	SOAP11ListRequest<Dictionary> getAllDictionaries();

	/**
	 * Gets the definition of a word from the specified dictionary.
	 * 
	 * @param dictionaryId
	 *            The id of the dictionary to query - this should be the same as
	 *            the ID retrieved from {@link Dictionary#getId()}
	 * @param word
	 *            The word to define, as a string.
	 * @return A request that will return the definition when executed.
	 */
	Request<Definition, DictionaryFault> getDefinition(String dictionaryId,
			String word);
}
