package com.alexgilleran.icesoap.examples.envelopes;

import com.alexgilleran.icesoap.envelope.impl.BaseSOAPEnvelopeDecorator;
import com.alexgilleran.icesoap.xml.XMLNode;

//<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:web="http://www.webserviceX.NET">
//<soapenv:Header/>
//<soapenv:Body>
// <web:GetCitiesByCountry>
//    <web:CountryName>Australia</web:CountryName>
// </web:GetCitiesByCountry>
//</soapenv:Body>
//</soapenv:Envelope>

public class GetCitiesEnvelope extends BaseSOAPEnvelopeDecorator {
	private final static String NAMESPACE = "http://www.webserviceX.NET";

	public GetCitiesEnvelope(CharSequence countryName) {
		super();

		declarePrefix("web", NAMESPACE);
		XMLNode getCitiesByCountry = getBody().addNode(NAMESPACE,
				"GetCitiesByCountry");
		getCitiesByCountry.addTextElement(NAMESPACE, "CountryName",
				countryName.toString());
	}
}
