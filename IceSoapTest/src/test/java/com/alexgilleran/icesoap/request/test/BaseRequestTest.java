package com.alexgilleran.icesoap.request.test;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertNull;

import java.io.InputStream;

import org.junit.Before;

import com.alexgilleran.icesoap.envelope.SOAPEnvelope;
import com.alexgilleran.icesoap.envelope.impl.PasswordSOAPEnvelope;
import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.exception.XMLParsingException;
import com.alexgilleran.icesoap.request.Request;
import com.alexgilleran.icesoap.request.RequestFactory;
import com.alexgilleran.icesoap.request.SOAPRequester;
import com.alexgilleran.icesoap.request.impl.RequestFactoryImpl;

public class BaseRequestTest<E> {

	protected static final String DUMMY_URL = "http://www.example.com/services/exampleservice";
	private RequestFactory requestFactory;
	private SOAPRequester mockRequester;

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
		SOAPEnvelope envelope = new PasswordSOAPEnvelope("username", "password");
		envelope.getBody().addNode("http://testns.com", "testname")
				.addTextNode(null, "textelement", "value");
		return envelope;
	}

	protected void doRequest(Request<E> request, InputStream inputStream)
			throws SOAPException, XMLParsingException {
		SOAPEnvelope envelope = getDummyEnvelope();

		expect(mockRequester.doSoapRequest(envelope, DUMMY_URL, null))
				.andReturn(
						new com.alexgilleran.icesoap.request.impl.Response(
								inputStream, 400));
		replay(mockRequester);

		request.execute();

		while (!request.isComplete()) {

		}

		assertNull(request.getException());
	}

	protected void doFailedRequest(Request<E> request, InputStream inputStream)
			throws SOAPException, XMLParsingException {
		SOAPEnvelope envelope = getDummyEnvelope();

		expect(mockRequester.doSoapRequest(envelope, DUMMY_URL, null))
				.andReturn(
						new com.alexgilleran.icesoap.request.impl.Response(
								inputStream, 500));
		replay(mockRequester);

		request.execute();

		while (!request.isComplete()) {

		}

		assertNull(request.getException());
	}

	public SOAPRequester getMockRequester() {
		return mockRequester;
	}
}