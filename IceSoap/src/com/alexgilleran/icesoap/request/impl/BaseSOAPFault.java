package com.alexgilleran.icesoap.request.impl;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

//<soap:Fault>
//<faultcode>soap:Client</faultcode>
//<faultstring>Unknown dictionary
//Parameter name: dictId</faultstring>
//<faultactor>http://services.aonaware.com/DictService/DictService.asmx</faultactor>
//<detail>
//   <Error xmlns="http://services.aonaware.com/webservices/">
//      <ErrorMessage>Unknown dictionary
//Parameter name: dictId</ErrorMessage>
//   </Error>
//</detail>
//</soap:Fault>

@XMLObject("//Fault")
public class BaseSOAPFault {
	@XMLField("faultcode")
	private String faultCode;
	@XMLField("faultstring")
	private String faultString;
	@XMLField("faultactor")
	private String faultActor;
}
