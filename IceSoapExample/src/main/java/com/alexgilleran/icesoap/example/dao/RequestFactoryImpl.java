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
import com.alexgilleran.icesoap.request.impl.ListRequestImpl;
import com.alexgilleran.icesoap.request.impl.RequestImpl;
import com.google.inject.Singleton;

@Singleton
public class RequestFactoryImpl implements RequestFactory {
	@InjectResource(R.string.soap_action_define)
	private String defineSoapAction;
	@InjectResource(R.string.soap_action_dictionary_list)
	private String dictionariesSoapAction;
	@InjectResource(R.string.url_dictionary_services)
	private String url;

	@Override
	public ListRequest<Dictionary> getAllDictionaries() {
		SOAPEnvelope envelope = new GetDictionariesEnvelope();

		return new ListRequestImpl<Dictionary>(url, envelope,
				dictionariesSoapAction, Dictionary.class);
	}

	@Override
	public Request<Definition> getDefinition(String dictionaryId, String word) {
		return new RequestImpl<Definition>(url, new DefineWordEnvelope(
				dictionaryId, word), defineSoapAction, Definition.class);
	}
}
