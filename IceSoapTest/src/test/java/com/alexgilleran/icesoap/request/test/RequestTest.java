package com.alexgilleran.icesoap.request.test;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.exception.XMLParsingException;
import com.alexgilleran.icesoap.observer.SOAPObserver;
import com.alexgilleran.icesoap.request.Request;
import com.alexgilleran.icesoap.request.impl.RequestImpl;
import com.alexgilleran.icesoap.request.test.xmlclasses.Response;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class RequestTest extends BaseRequestTest<Response> {
	private Response expectedResponse = new Response(1, "Text");

	@SuppressWarnings("unchecked")
	@Test
	public void testRequest() throws SOAPException, XMLParsingException {
		// Set up a parser for the response
		Request<Response> request = new RequestImpl<Response>(DUMMY_URL,
				getDummyEnvelope(), null, Response.class);

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
}
