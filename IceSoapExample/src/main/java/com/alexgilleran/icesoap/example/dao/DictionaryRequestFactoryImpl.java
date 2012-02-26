package com.alexgilleran.icesoap.example.dao;

import roboguice.inject.InjectResource;

import com.alexgilleran.icesoap.envelope.SOAPEnvelope;
import com.alexgilleran.icesoap.example.R;
import com.alexgilleran.icesoap.example.domain.Definition;
import com.alexgilleran.icesoap.example.domain.Dictionary;
import com.alexgilleran.icesoap.example.envelopes.DefineWordEnvelope;
import com.alexgilleran.icesoap.example.envelopes.GetDictionariesEnvelope;
import com.alexgilleran.icesoap.request.SOAP11ListRequest;
import com.alexgilleran.icesoap.request.SOAP11Request;
import com.alexgilleran.icesoap.request.RequestFactory;
import com.alexgilleran.icesoap.request.SOAPRequester;
import com.alexgilleran.icesoap.request.impl.RequestFactoryImpl;
import com.google.inject.Singleton;

/**
 * Implementation of {@link DictionaryRequestFactory}
 * 
 * @author Alex Gilleran
 * 
 */
@Singleton
public class DictionaryRequestFactoryImpl implements DictionaryRequestFactory {
	/** The SOAP Action for the define service */
	@InjectResource(R.string.soap_action_define)
	private String defineSoapAction;
	/** The SOAP Action for the dictionary list service */
	@InjectResource(R.string.soap_action_dictionary_list)
	private String dictionariesSoapAction;
	/** The URL for all dictionary services */
	@InjectResource(R.string.url_dictionary_services)
	private String url;

	/**
	 * Factory for making basic requests - if you want to use some other way of
	 * retrieving XML from the web service (apart from the Apache HTTP Client)
	 * you can extend this and put in your own implementation of
	 * {@link SOAPRequester}.
	 */
	private RequestFactory requestFactory = new RequestFactoryImpl();

	@Override
	public SOAP11ListRequest<Dictionary> getAllDictionaries() {
		SOAPEnvelope envelope = new GetDictionariesEnvelope();
		return requestFactory.buildListRequest(url, envelope,
				dictionariesSoapAction, Dictionary.class);
	}

	@Override
	public SOAP11Request<Definition> getDefinition(String dictionaryId, String word) {
		return requestFactory.buildRequest(url, new DefineWordEnvelope(
				dictionaryId, word), defineSoapAction, Definition.class);
	}
}
