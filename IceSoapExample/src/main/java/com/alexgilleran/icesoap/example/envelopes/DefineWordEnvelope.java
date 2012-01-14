package com.alexgilleran.icesoap.example.envelopes;

import com.alexgilleran.icesoap.xml.XMLParentNode;

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
		XMLParentNode defineInDict = getBody().addParentNode(getAonAwareNamespace(),
				"DefineInDict");
		defineInDict.addTextNode(getAonAwareNamespace(), "dictId", dictId);
		defineInDict.addTextNode(getAonAwareNamespace(), "word", word);
	}
}
