package com.alexgilleran.icesoap.requester;

import java.io.IOException;
import java.io.InputStream;

import com.alexgilleran.icesoap.envelope.SOAPEnv;


public interface SOAPRequester {
	public InputStream doSoapRequest(SOAPEnv envelope, String soapAction) throws IOException;
}
