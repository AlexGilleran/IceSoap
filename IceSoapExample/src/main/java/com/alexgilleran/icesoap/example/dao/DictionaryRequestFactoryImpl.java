package com.alexgilleran.icesoap.example.dao;

import roboguice.inject.InjectResource;

import com.alexgilleran.icesoap.envelope.SOAPEnvelope;
import com.alexgilleran.icesoap.example.R;
import com.alexgilleran.icesoap.example.domain.Definition;
import com.alexgilleran.icesoap.example.domain.Dictionary;
import com.alexgilleran.icesoap.example.envelopes.DefineWordEnvelope;
import com.alexgilleran.icesoap.example.envelopes.GetDictionariesEnvelope;
import com.alexgilleran.icesoap.request.ListRequest;
import com.alexgilleran.icesoap.request.Request;
import com.alexgilleran.icesoap.request.RequestFactory;
import com.alexgilleran.icesoap.request.impl.RequestFactoryImpl;
import com.google.inject.Singleton;

@Singleton
public class DictionaryRequestFactoryImpl implements DictionaryRequestFactory {
	@InjectResource(R.string.soap_action_define)
	private String defineSoapAction;
	@InjectResource(R.string.soap_action_dictionary_list)
	private String dictionariesSoapAction;
	@InjectResource(R.string.url_dictionary_services)
	private String url;

	private RequestFactory requestFactory = new RequestFactoryImpl();

	@Override
	public ListRequest<Dictionary> getAllDictionaries() {
		SOAPEnvelope envelope = new GetDictionariesEnvelope();

		return requestFactory.buildListRequest(url, envelope,
				dictionariesSoapAction, Dictionary.class);
	}

	@Override
	public Request<Definition> getDefinition(String dictionaryId, String word) {
		return requestFactory.buildRequest(url, new DefineWordEnvelope(
				dictionaryId, word), defineSoapAction, Definition.class);
	}
}
