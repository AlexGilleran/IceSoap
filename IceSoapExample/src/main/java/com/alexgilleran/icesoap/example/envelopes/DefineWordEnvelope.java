package com.alexgilleran.icesoap.example.envelopes;

import com.alexgilleran.icesoap.xml.XMLParentNode;

// The envelope being created: 
//<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:web="http://services.aonaware.com/webservices/">
//<soapenv:Header/>
//<soapenv:Body>
//   <web:DefineInDict>
//      <web:dictId>jargon</web:dictId>
//      <web:word>scsi</web:word>
//   </web:DefineInDict>
//</soapenv:Body>
//</soapenv:Envelope>

/**
 * Envelope used to get a word definition from the service.
 */
public class DefineWordEnvelope extends BaseDictionaryEnvelope {

	/**
	 * Defines a new DefineWordEnvelope, creating the necessary XML nodes.
	 * 
	 * @param dictId
	 *            The ID of the dictionary (as specified by the service) to use
	 *            for the definition lookup.
	 * @param word
	 *            The word to lookup.
	 */
	public DefineWordEnvelope(String dictId, String word) {
		XMLParentNode defineInDict = getBody().addNode(
				getAonAwareNamespace(), "DefineInDict");
		defineInDict.addTextNode(getAonAwareNamespace(), "dictId", dictId);
		defineInDict.addTextNode(getAonAwareNamespace(), "word", word);
	}
}
