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
import com.alexgilleran.icesoap.request.SOAPRequester;
import com.alexgilleran.icesoap.request.impl.ApacheSOAPRequester;

public class ApacheSOAPRequesterTest {
	private String encoding;
	private SOAPEnvelope envelope;

	@Test
	public void testUtf8Encoding() throws ClientProtocolException, IOException {
		encoding = "UTF-8";
		envelope = buildDifficultEnvelope(encoding);
		SOAPRequester requester = new TestApacheSOAPRequester();
		requester.doSoapRequest(envelope, "http://target.com");
	}

	@Test
	public void testUtf16Encoding() throws ClientProtocolException, IOException {
		encoding = "UTF-16";
		envelope = buildDifficultEnvelope(encoding);
		SOAPRequester requester = new TestApacheSOAPRequester();
		requester.doSoapRequest(envelope, "http://target.com");
	}

	private SOAPEnvelope buildDifficultEnvelope(String encoding) {
		SOAPEnvelope env = new BaseSOAP11Envelope();
		env.setEncoding(encoding);
		env.getBody().addTextNode(null, "ÀÁÂÃÄÅÆÇÈÉýÿĂĄ", "ɑɔʥʣʨʪɯ");
		env.getBody().addTextNode(null, "ѨѫѯРсшНЌЄЏ", "ڝڠڥکۛ٢شظڧ۞۸");
		return env;
	}

	private class TestApacheSOAPRequester extends ApacheSOAPRequester {
		@Override
		protected HttpClient buildHttpClient() {
			return new MockHttpClient(encoding, envelope);
		}
	}

	private class MockHttpClient implements HttpClient {
		private String encoding;
		private SOAPEnvelope envelope;

		public MockHttpClient(String encoding, SOAPEnvelope envelope) {
			this.encoding = encoding;
			this.envelope = envelope;
		}

		@Override
		public HttpResponse execute(HttpUriRequest httpUriRequest) throws IOException, ClientProtocolException {
			HttpPost httpPost = (HttpPost) httpUriRequest;

			Assert.assertEquals("text/xml; charset=" + ApacheSOAPRequesterTest.this.encoding,
					httpPost.getHeaders(ApacheSOAPRequester.CONTENT_TYPE_LABEL)[0].getValue());

			InputStream is = httpPost.getEntity().getContent();
			byte[] streamOutput = new byte[(int) httpPost.getEntity().getContentLength()];

			int i;
			do {
				i = is.read(streamOutput);
			} while (i > 0);

			String output = new String(streamOutput, encoding);
			Assert.assertEquals(envelope.toString(), output);

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
