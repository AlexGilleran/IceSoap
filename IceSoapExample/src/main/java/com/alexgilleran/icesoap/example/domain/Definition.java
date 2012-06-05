package com.alexgilleran.icesoap.example.domain;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;
import com.alexgilleran.icesoap.example.processors.DefinitionProcessor;

// The envelope being imitated:
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

/**
 * A definition of a word.
 */
@XMLObject("//Definitions/Definition")
public class Definition {
	/** The word being defined */
	@XMLField("Word")
	private String word;

	/**
	 * The dictionary being used for the definition - note that the entire
	 * Dictionary type is parsed here, according to the {@link XMLField}
	 * annotations in {@link Dictionary}
	 */
	@XMLField("Dictionary")
	private Dictionary dictionary;

	/** The definition of the word */
	@XMLField(value = "WordDefinition", processor = DefinitionProcessor.class)
	private String wordDefinition;

	/**
	 * 
	 * @return The word being defined
	 */
	public String getWord() {
		return word;
	}

	/**
	 * 
	 * @return The dictionary providing the definition.
	 */
	public Dictionary getDictionary() {
		return dictionary;
	}

	/**
	 * 
	 * @return The definition as a String
	 */
	public String getWordDefinition() {
		return wordDefinition;
	}
}
