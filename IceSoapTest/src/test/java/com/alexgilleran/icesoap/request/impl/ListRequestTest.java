package com.alexgilleran.icesoap.request.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.alexgilleran.icesoap.request.SOAP11ListRequest;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.alexgilleran.icesoap.exception.XMLParsingException;
import com.alexgilleran.icesoap.observer.SOAP11ListObserver;
import com.alexgilleran.icesoap.request.impl.xmlclasses.Response;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class ListRequestTest extends BaseRequestTest<List<Response>> {
	private List<Response> expectedList = Arrays.asList(
			new Response(1, "Text1"), new Response(2, "Text2"), new Response(3,
					"Text3"), new Response(4, "Text4"));

	@Test
	@SuppressWarnings("unchecked")
	public void testRequest() throws XMLParsingException, IOException {
		// Set up a parser for the response
		SOAP11ListRequest<Response> request = getRequestFactory().buildListRequest(
				DUMMY_URL, getDummyEnvelope(), SOAP_ACTION, Response.class);

		// Create a mock observer and put in the expected call (we expect it to
		// come back with the request)
		SOAP11ListObserver<Response> mockObserver = createMock(SOAP11ListObserver.class);
		mockObserver.onNewItem(request, expectedList.get(0));
		mockObserver.onNewItem(request, expectedList.get(1));
		mockObserver.onNewItem(request, expectedList.get(2));
		mockObserver.onNewItem(request, expectedList.get(3));
		mockObserver.onCompletion(request);
		replay(mockObserver);

		// Register the observer to the request
		request.registerObserver(mockObserver);

		// Do the request
		doRequest(request, SampleResponse.getListResponse());

		// Verify that it did what it was supposed to
		verify(mockObserver);

		// Verify the parsed object was correct.
		assertEquals(expectedList, request.getResult());
	}
}
