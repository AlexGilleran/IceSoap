package com.alexgilleran.icesoap.request.test;

import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.alexgilleran.icesoap.envelope.SOAPEnvelope;
import com.alexgilleran.icesoap.envelope.impl.PasswordSOAPEnvelope;
import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.observer.SOAPObserver;
import com.alexgilleran.icesoap.parser.IceSoapParser;
import com.alexgilleran.icesoap.parser.impl.IceSoapParserImpl;
import com.alexgilleran.icesoap.request.Request;
import com.alexgilleran.icesoap.request.impl.RequestImpl;
import com.alexgilleran.icesoap.request.test.xmlclasses.Response;
import com.alexgilleran.icesoap.requester.SOAPRequester;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class RequestTest {
	private static final String DUMMY_URL = "http://www.example.com/services/exampleservice";
	private SOAPRequester mockRequester;
	private SOAPObserver<Response> mockObserver;
	private Response expectedResponse = new Response(1, "Text");

	@Before
	public void setUp() {
		mockRequester = createMock(SOAPRequester.class);
		mockObserver = createMock(SOAPObserver.class);
	}

	@Test
	public void testRequest() throws SOAPException {
		SOAPEnvelope envelope = new PasswordSOAPEnvelope("username", "password");
		envelope.getBody().addNode("http://testns.com", "testname")
				.addTextElement(null, "textelement", "value");

		IceSoapParser<Response> parser = new IceSoapParserImpl<Response>(
				Response.class);
		Request<Response> request = new RequestImpl<Response>(DUMMY_URL,
				parser, envelope);
		request.setSoapRequester(mockRequester);
		request.registerObserver(mockObserver);

		mockObserver.onCompletion(request);
		expect(mockRequester.doSoapRequest(envelope, DUMMY_URL)).andReturn(
				SampleResponse.getSingleResponse());
		replay(mockObserver);
		replay(mockRequester);

		request.execute();

		while (!request.isComplete()) {

		}

		assertNull(request.getException());

		verify(mockObserver);
		verify(mockRequester);

		assertEquals(expectedResponse, request.getResult());
	}
}
