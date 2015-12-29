package com.alexgilleran.icesoap.request.impl;

import com.alexgilleran.icesoap.envelope.SOAPEnvelope;
import com.alexgilleran.icesoap.envelope.impl.BaseSOAP11Envelope;

/**
 * Constants used by tests for {@link com.alexgilleran.icesoap.request.SOAPRequester}s.
 *
 * @author Alex Gilleran
 */
abstract class BaseSOAPRequesterTest {
	protected final static String UTF_8 = "UTF-8";
	protected final static String UTF_16 = "UTF-16";
	protected final static String SOAP11_MIME_TYPE = "text/xml";
	protected final static String SOAP12_MIME_TYPE = "application/soap+xml";

	protected SOAPEnvelope buildDifficultEnvelope(String encoding) {
		SOAPEnvelope env = new BaseSOAP11Envelope();
		env.setEncoding(encoding);
		env.getBody().addTextNode(null, "ÀÁÂÃÄÅÆÇÈÉýÿĂĄ", "ɑɔʥʣʨʪɯ");
		env.getBody().addTextNode(null, "ѨѫѯРсшНЌЄЏ", "ڝڠڥکۛ٢شظڧ۞۸");
		return env;
	}
}
