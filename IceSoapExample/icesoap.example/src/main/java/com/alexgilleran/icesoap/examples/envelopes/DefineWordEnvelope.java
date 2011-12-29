package com.alexgilleran.icesoap.examples.envelopes;

import com.alexgilleran.icesoap.xml.XMLNode;

//<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:web="http://services.aonaware.com/webservices/">
//<soapenv:Header/>
//<soapenv:Body>
//   <web:DefineInDict>
//      <web:dictId>jargon</web:dictId>
//      <web:word>scsi</web:word>
//   </web:DefineInDict>
//</soapenv:Body>
//</soapenv:Envelope>
public class DefineWordEnvelope extends BaseDictionaryEnvelope {
	public DefineWordEnvelope(String dictId, String word) {
		XMLNode defineInDict = getBody().addNode(getAonAwareNamespace(),
				"DefineInDict");
		defineInDict.addTextElement(getAonAwareNamespace(), "dictId", dictId);
		defineInDict.addTextElement(getAonAwareNamespace(), "word", word);
	}
}
