package com.alexgilleran.icesoap.request.impl;

import com.alexgilleran.icesoap.envelope.SOAPEnvelope;
import com.alexgilleran.icesoap.envelope.impl.BaseSOAP11Envelope;
import com.alexgilleran.icesoap.envelope.impl.BaseSOAP12Envelope;
import com.alexgilleran.icesoap.request.Response;
import com.alexgilleran.icesoap.request.SOAPRequester;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;
import org.junit.matchers.*;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

/**
 * Tests {@link HUCSOAPRequester}.
 */
@RunWith(RobolectricTestRunner.class)
public class HUCSOAPRequesterTest extends BaseSOAPRequesterTest {
	private static final String TARGET_URL = "http://target.com";

	// Default values, these can be overwritten later
	private SOAPRequester requester;
	private HttpURLConnection mockConn;

	@Before
	public void setUp() {
		requester = new MockingRequester();
		mockConn = createNiceMock(HttpURLConnection.class);
	}

	@Test
	public void testUtf8Encoding() throws IOException {
		doTest(buildDifficultEnvelope(UTF_8), UTF_8, SOAP11_MIME_TYPE);
	}

	@Test
	public void testUtf16Encoding() throws IOException {
		doTest(buildDifficultEnvelope(UTF_16), UTF_16, SOAP11_MIME_TYPE);
	}

	@Test
	public void testSoap11MimeType() throws IOException {
		doTest(new BaseSOAP11Envelope(), UTF_8, SOAP11_MIME_TYPE);
	}

	@Test
	public void testSoap12MimeType() throws IOException {
		doTest(new BaseSOAP12Envelope(), UTF_8, SOAP12_MIME_TYPE);
	}

	@Test
	public void testBadHttpCode() throws IOException {
		OutputStream mockStream = createMock(OutputStream.class);
		InputStream is = createMock(InputStream.class);

		expect(mockConn.getOutputStream()).andReturn(mockStream);
		expect(mockConn.getInputStream()).andReturn(is);
		expect(mockConn.getResponseCode()).andReturn(500);
		replay(mockConn);

		Response response = requester.doSoapRequest(new BaseSOAP11Envelope(), TARGET_URL);

		assertEquals(Response.Status.SOAP_FAULT, response.getStatus());
	}

	private void doTest(SOAPEnvelope expectedEnvelope, String expectedEncoding, String mimeType) throws IOException {
		mockConn.setRequestProperty("Content-type", mimeType + "; charset=" + expectedEncoding);
		EasyMock.expectLastCall();

		OutputStream mockStream = createMock(OutputStream.class);
		expect(mockConn.getOutputStream()).andReturn(mockStream);

		mockStream.write(EasyMock.aryEq(expectedEnvelope.toString().getBytes(expectedEncoding)));
		EasyMock.expectLastCall();

		mockStream.flush();
		EasyMock.expectLastCall();

		mockStream.close();
		EasyMock.expectLastCall();

		InputStream is = createMock(InputStream.class);
		expect(mockConn.getInputStream()).andReturn(is);

		expect(mockConn.getResponseCode()).andReturn(200);

		replay(mockConn);
		replay(mockStream);

		Response response = requester.doSoapRequest(expectedEnvelope, TARGET_URL);

		assertEquals(is, response.getData());
		assertEquals(Response.Status.OK, response.getStatus());
	}

	private class MockingRequester extends HUCSOAPRequester {
		@Override
		protected HttpURLConnection buildHttpUrlConnection(URL url) throws IOException {
			assertEquals(TARGET_URL, url.toString());
			return mockConn;
		}
	}

	;
}
