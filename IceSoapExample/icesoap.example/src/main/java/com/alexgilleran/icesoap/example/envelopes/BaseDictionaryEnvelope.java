package com.alexgilleran.icesoap.example.envelopes;

import com.alexgilleran.icesoap.envelope.impl.BaseSOAPEnvelope;

public class BaseDictionaryEnvelope extends BaseSOAPEnvelope {
	private final static String AON_AWARE_NAMESPACE = "http://services.aonaware.com/webservices/";

	public BaseDictionaryEnvelope() {
		super();

		declarePrefix("web", AON_AWARE_NAMESPACE);
	}

	protected String getAonAwareNamespace() {
		return AON_AWARE_NAMESPACE;
	}
}
