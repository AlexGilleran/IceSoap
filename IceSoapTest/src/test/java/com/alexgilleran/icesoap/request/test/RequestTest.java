package com.alexgilleran.icesoap.request.test;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.exception.XMLParsingException;
import com.alexgilleran.icesoap.observer.SOAPObserver;
import com.alexgilleran.icesoap.request.Request;
import com.alexgilleran.icesoap.request.test.xmlclasses.Response;
import com.alexgilleran.icesoap.soapfault.SOAP11Fault;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class RequestTest extends BaseRequestTest<Response> {
	private Response expectedResponse = new Response(1, "Text");
	private SOAP11Fault fault = new SOAP11Fault(SampleResponse.FAULT_CODE,
			SampleResponse.FAULT_STRING, SampleResponse.FAULT_ACTOR);

	@SuppressWarnings("unchecked")
	@Test
	public void testRequest() throws IOException, XMLParsingException {
		// Set up a parser for the response
		Request<Response> request = getRequestFactory().buildRequest(DUMMY_URL,
				getDummyEnvelope(), SOAP_ACTION, Response.class);

		// Create a mock observer and put in the expected call (we expect it to
		// come back with the request)
		SOAPObserver<Response> mockObserver = createMock(SOAPObserver.class);
		mockObserver.onCompletion(request);
		replay(mockObserver);

		// Register the observer to the request
		request.registerObserver(mockObserver);

		// Do the request
		doRequest(request, SampleResponse.getSingleResponse());

		// Verify that it did what it was supposed to
		verify(mockObserver);

		// Verify the parsed object was correct.
		assertEquals(expectedResponse, request.getResult());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFailedRequestSOAP11() throws IOException,
			XMLParsingException {
		// Set up a parser for the response
		Request<Response> request = getRequestFactory().buildRequest(DUMMY_URL,
				getDummyEnvelope(), SOAP_ACTION, Response.class);

		// Create a mock observer and put in the expected call (we expect it to
		// come back with the request)
		SOAPObserver<Response> mockObserver = createMock(SOAPObserver.class);
		mockObserver.onException(eq(request), isA(SOAPException.class));
		replay(mockObserver);

		// Register the observer to the request
		request.registerObserver(mockObserver);

		// Do the request
		doFailedRequest(request, SampleResponse.getSoap11Fault());

		// Verify that it did what it was supposed to
		verify(mockObserver);

		// Verify the parsed object was correct.
		assertEquals(fault, request.getSOAPFault());
	}
}
