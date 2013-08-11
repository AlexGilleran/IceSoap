package com.alexgilleran.icesoap.request.test;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.io.InputStream;

import junit.framework.Assert;

import org.junit.Before;

import com.alexgilleran.icesoap.envelope.SOAPEnvelope;
import com.alexgilleran.icesoap.envelope.impl.PasswordSOAP11Envelope;
import com.alexgilleran.icesoap.exception.XMLParsingException;
import com.alexgilleran.icesoap.request.Request;
import com.alexgilleran.icesoap.request.RequestFactory;
import com.alexgilleran.icesoap.request.SOAPRequester;
import com.alexgilleran.icesoap.request.impl.RequestFactoryImpl;

public class BaseRequestTest<E> {

	private static final int HTTP_ERROR_500 = 500;
	protected static final String DUMMY_URL = "http://www.example.com/services/exampleservice";
	protected static final String SOAP_ACTION = "soapaction";

	private RequestFactory requestFactory;
	protected SOAPRequester mockRequester;

	@Before
	public void setUp() {
		mockRequester = createMock(SOAPRequester.class);
		requestFactory = new RequestFactoryImpl(mockRequester);
	}

	protected RequestFactory getRequestFactory() {
		return requestFactory;
	}

	protected SOAPEnvelope getDummyEnvelope() {
		// Set up an envelope to send
		SOAPEnvelope envelope = new PasswordSOAP11Envelope("username", "password");
		envelope.getBody().addNode("http://testns.com", "testname").addTextNode(null, "textelement", "value");
		return envelope;
	}

	protected void doRequest(Request<E, ?> request, InputStream inputStream) throws IOException, XMLParsingException {
		doRequest(request, inputStream, getDummyEnvelope());
	}

	protected void doRequest(Request<E, ?> request, InputStream inputStream, SOAPEnvelope envelope) throws IOException,
			XMLParsingException {

		expect(mockRequester.doSoapRequest(envelope, DUMMY_URL, SOAP_ACTION)).andReturn(
				new com.alexgilleran.icesoap.request.impl.Response(inputStream, 200));
		replay(mockRequester);

		request.execute();

		while (!request.isComplete()) {

		}

		assertNull(request.getException());
	}

	protected <FaultType> void doFailedRequest(Request<E, FaultType> request, InputStream inputStream)
			throws IOException, XMLParsingException {
		doFailedRequest(request, inputStream, HTTP_ERROR_500);
	}

	protected <FaultType> void doFailedRequest(Request<E, FaultType> request, InputStream inputStream, int errorCode)
			throws IOException, XMLParsingException {
		SOAPEnvelope envelope = getDummyEnvelope();

		expect(mockRequester.doSoapRequest(envelope, DUMMY_URL, SOAP_ACTION)).andReturn(
				new com.alexgilleran.icesoap.request.impl.Response(inputStream, errorCode));
		replay(mockRequester);

		request.execute();

		while (!request.isComplete()) {

		}
	}

	protected void doExceptionRequest(Request<E, ?> request, InputStream inputStream) throws IOException,
			XMLParsingException {
		IOException ioException = new IOException("Test");

		SOAPEnvelope envelope = getDummyEnvelope();

		expect(mockRequester.doSoapRequest(envelope, DUMMY_URL, SOAP_ACTION)).andThrow(ioException);

		replay(mockRequester);

		request.execute();

		while (!request.isComplete()) {

		}

		Assert.assertNotNull(request.getException());
	}

	public SOAPRequester getMockRequester() {
		return mockRequester;
	}
}