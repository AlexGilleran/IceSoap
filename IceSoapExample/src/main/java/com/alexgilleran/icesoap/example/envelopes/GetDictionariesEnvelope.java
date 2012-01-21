package com.alexgilleran.icesoap.example.envelopes;

import com.alexgilleran.icesoap.envelope.SOAPEnvelope;
import com.alexgilleran.icesoap.envelope.impl.BaseSOAPEnvelope;

// The envelope being created:
//<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:web="http://services.aonaware.com/webservices/">
//<soapenv:Header/>
//<soapenv:Body>
//   <web:DictionaryList/>
//</soapenv:Body>
//</soapenv:Envelope>

/**
 * An envelope that gets a list of dictionaries from the service.
 * 
 * This envelope is actually very simple - it could've just as easily been
 * created by instantiating a new {@link BaseSOAPEnvelope} and using the public
 * methods on the {@link SOAPEnvelope} interface to build up the envelope.
 */
public class GetDictionariesEnvelope extends BaseDictionaryEnvelope {

	/**
	 * Instantiates a new GetDictionariesObject - no inputs are needed.
	 */
	public GetDictionariesEnvelope() {
		super();

		getBody().addParentNode(super.getAonAwareNamespace(), "DictionaryList");
	}
}
