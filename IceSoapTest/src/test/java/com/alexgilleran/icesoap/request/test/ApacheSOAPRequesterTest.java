package com.alexgilleran.icesoap.request.test;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.alexgilleran.icesoap.envelope.SOAPEnvelope;
import com.alexgilleran.icesoap.envelope.impl.BaseSOAP11Envelope;
import com.alexgilleran.icesoap.envelope.impl.BaseSOAP12Envelope;
import com.alexgilleran.icesoap.request.SOAPRequester;
import com.alexgilleran.icesoap.request.impl.ApacheSOAPRequester;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class ApacheSOAPRequesterTest {
	private static final String UTF_8 = "UTF-8";
	private static final String UTF_16 = "UTF-16";
	private static final String SOAP11_MIME_TYPE = "text/xml";
	private static final String SOAP12_MIME_TYPE = "application/soap+xml";

	// Default values, these can be overwritten later
	private String expectedMimeType = SOAP11_MIME_TYPE;
	private String expectedEncoding = UTF_8;
	private MockHttpClient httpClient;
	private SOAPEnvelope expectedEnvelope;
	private SOAPRequester requester;

	@Before
	public void setUp() {
		httpClient = new MockHttpClient();
		requester = new TestApacheSOAPRequester();
	}

	@Test
	public void testUtf8Encoding() throws ClientProtocolException, IOException {
		expectedEnvelope = buildDifficultEnvelope();

		requester.doSoapRequest(expectedEnvelope, "http://target.com");
	}

	@Test
	public void testUtf16Encoding() throws ClientProtocolException, IOException {
		expectedEncoding = UTF_16;
		expectedEnvelope = buildDifficultEnvelope();

		requester.doSoapRequest(expectedEnvelope, "http://target.com");
	}

	@Test
	public void testSoap11MimeType() throws ClientProtocolException, IOException {
		expectedEnvelope = new BaseSOAP11Envelope();
		SOAPRequester requester = new TestApacheSOAPRequester();
		requester.doSoapRequest(expectedEnvelope, "http://target.com");
	}

	@Test
	public void testSoap12MimeType() throws ClientProtocolException, IOException {
		expectedEnvelope = new BaseSOAP12Envelope();
		expectedMimeType = SOAP12_MIME_TYPE;
		SOAPRequester requester = new TestApacheSOAPRequester();
		requester.doSoapRequest(expectedEnvelope, "http://target.com");
	}

	private SOAPEnvelope buildDifficultEnvelope() {
		SOAPEnvelope env = new BaseSOAP11Envelope();
		env.setEncoding(expectedEncoding);
		env.getBody().addTextNode(null, "ÀÁÂÃÄÅÆÇÈÉýÿĂĄ", "ɑɔʥʣʨʪɯ");
		env.getBody().addTextNode(null, "ѨѫѯРсшНЌЄЏ", "ڝڠڥکۛ٢شظڧ۞۸");
		return env;
	}

	private class TestApacheSOAPRequester extends ApacheSOAPRequester {

		@Override
		protected HttpClient buildHttpClient() {
			return httpClient;
		}
	}

	private class MockHttpClient implements HttpClient {
		@Override
		public HttpResponse execute(HttpUriRequest httpUriRequest) throws IOException, ClientProtocolException {
			HttpPost httpPost = (HttpPost) httpUriRequest;

			Assert.assertEquals(expectedMimeType + "; charset=" + expectedEncoding,
					httpPost.getHeaders(ApacheSOAPRequester.CONTENT_TYPE_LABEL)[0].getValue());

			InputStream is = httpPost.getEntity().getContent();
			byte[] streamOutput = new byte[(int) httpPost.getEntity().getContentLength()];

			int i;
			do {
				i = is.read(streamOutput);
			} while (i > 0);

			String output = new String(streamOutput, expectedEncoding);
			Assert.assertEquals(expectedEnvelope.toString(), output);

			HttpResponse mockResponse = createMock(HttpResponse.class);
			HttpEntity mockEntity = createMock(HttpEntity.class);
			StatusLine statusLine = createMock(StatusLine.class);

			expect(mockResponse.getEntity()).andReturn(mockEntity);
			expect(mockEntity.isRepeatable()).andReturn(false);
			expect(mockEntity.getContent()).andReturn(is);
			expect(mockEntity.getContentLength()).andReturn(0l);
			expect(mockEntity.getContentLength()).andReturn(0l);
			expect(mockResponse.getStatusLine()).andReturn(statusLine);

			replay(mockResponse);
			replay(mockEntity);

			return mockResponse;
		}

		@Override
		public HttpResponse execute(HttpUriRequest arg0, HttpContext arg1) throws IOException, ClientProtocolException {
			// Do nothing, we aren't testing this.
			return null;
		}

		@Override
		public HttpResponse execute(HttpHost arg0, HttpRequest arg1) throws IOException, ClientProtocolException {
			// Do nothing, we aren't testing this.
			return null;
		}

		@Override
		public <T> T execute(HttpUriRequest arg0, ResponseHandler<? extends T> arg1) throws IOException,
				ClientProtocolException {
			// Do nothing, we aren't testing this.
			return null;
		}

		@Override
		public HttpResponse execute(HttpHost arg0, HttpRequest arg1, HttpContext arg2) throws IOException,
				ClientProtocolException {
			// Do nothing, we aren't testing this.
			return null;
		}

		@Override
		public <T> T execute(HttpUriRequest arg0, ResponseHandler<? extends T> arg1, HttpContext arg2)
				throws IOException, ClientProtocolException {
			// Do nothing, we aren't testing this.
			return null;
		}

		@Override
		public <T> T execute(HttpHost arg0, HttpRequest arg1, ResponseHandler<? extends T> arg2) throws IOException,
				ClientProtocolException {
			// Do nothing, we aren't testing this.
			return null;
		}

		@Override
		public <T> T execute(HttpHost arg0, HttpRequest arg1, ResponseHandler<? extends T> arg2, HttpContext arg3)
				throws IOException, ClientProtocolException {
			// Do nothing, we aren't testing this.
			return null;
		}

		@Override
		public ClientConnectionManager getConnectionManager() {
			// Do nothing, we aren't testing this.
			return null;
		}

		@Override
		public HttpParams getParams() {
			// Do nothing, we aren't testing this.
			return null;
		}

	};
}
