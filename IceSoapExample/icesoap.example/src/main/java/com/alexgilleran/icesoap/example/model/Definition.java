package com.alexgilleran.icesoap.example.model;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

//<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
//<soap:Body>
//   <DefineInDictResponse xmlns="http://services.aonaware.com/webservices/">
//      <DefineInDictResult>
//         <Word>scsi</Word>
//         <Definitions>
//            <Definition>
//               <Word>scsi</Word>
//               <Dictionary>
//                  <Id>jargon</Id>
//                  <Name>Jargon File (4.3.1, 29 Jun 2001)</Name>
//               </Dictionary>
//               <WordDefinition>SCSI n. [Small Computer System Interface] A bus-independent standard
//for system-level interfacing between a computer and intelligent devices.
//Typically annotated in literature with `sexy' (/sek'see/), `sissy'
//(/sis'ee/), and `scuzzy' (/skuh'zee/) as pronunciation guides -- the
//last being the overwhelmingly predominant form, much to the dismay of
//the designers and their marketing people. One can usually assume that a
//person who pronounces it /S-C-S-I/ is clueless.</WordDefinition>
//            </Definition>
//         </Definitions>
//      </DefineInDictResult>
//   </DefineInDictResponse>
//</soap:Body>
//</soap:Envelope>

@XMLObject("//Definitions/Definition")
public class Definition {
	@XMLField("Word")
	private String word;
	@XMLField("Dictionary")
	private Dictionary dictionary;
	@XMLField("WordDefinition")
	private String wordDefinition;

	public String getWord() {
		return word;
	}

	public Dictionary getDictionary() {
		return dictionary;
	}

	public String getWordDefinition() {
		return wordDefinition;
	}
}
