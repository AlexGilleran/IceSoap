package com.alexgilleran.icesoap.examples.envelopes;

//<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:web="http://services.aonaware.com/webservices/">
//<soapenv:Header/>
//<soapenv:Body>
//   <web:DictionaryList/>
//</soapenv:Body>
//</soapenv:Envelope>

public class GetDictionariesEnvelope extends BaseDictionaryEnvelope {

	public GetDictionariesEnvelope() {
		super();

		getBody().addNode(super.getAonAwareNamespace(), "DictionaryList");
	}
}
