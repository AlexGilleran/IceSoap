package com.alexgilleran.icesoap.example.envelopes;

import com.alexgilleran.icesoap.envelope.impl.BaseSOAP11Envelope;

/**
 * Base class for envelopes used to communicate with the dictionary service.
 * 
 * Often when communicating with a web service, the envelopes that you send will
 * have a lot of common information. By using an abstract base class, you can
 * keep the logic to generate this in one place.
 * 
 * @author Alex Gilleran
 * 
 */
public abstract class BaseDictionaryEnvelope extends BaseSOAP11Envelope {
	/** The namespace of the AonAware services */
	private final static String AON_AWARE_NAMESPACE = "http://services.aonaware.com/webservices/";

	/**
	 * Instantiates the {@link BaseDictionaryEnvelope} - declares the AonAware
	 * namespace for later use.
	 */
	public BaseDictionaryEnvelope() {
		declarePrefix("web", AON_AWARE_NAMESPACE);
	}

	/**
	 * 
	 * @return The AonAware namespace used by AonAware XML Objects.
	 */
	protected String getAonAwareNamespace() {
		return AON_AWARE_NAMESPACE;
	}
}
