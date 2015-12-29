package com.alexgilleran.icesoap.request.impl;

import com.alexgilleran.icesoap.envelope.SOAPEnvelope;
import com.alexgilleran.icesoap.request.Response;
import com.alexgilleran.icesoap.request.SOAPRequester;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Singleton implementation of {@link SOAPRequester}, using the Apache HTTP
 * Client.
 *
 * @author Alex Gilleran
 */
public class ApacheSOAPRequester implements SOAPRequester, HTTPDefaults {

	/**
	 * Apache HTTP Client for making HTTP requests.
	 */
	private HttpClient httpClient;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Response doSoapRequest(SOAPEnvelope envelope, String targetUrl) throws IOException {
		return doSoapRequest(envelope, targetUrl, BLANK_SOAP_ACTION);
	}

	/**
	 * {@inheritDoc}
	 */
	public Response doSoapRequest(SOAPEnvelope envelope, String targetUrl, String soapAction) throws IOException {
		return doHttpPost(buildPostRequest(targetUrl, envelope, soapAction));
	}

	/**
	 * Performs an HTTP POST request
	 *
	 * @param httpPost The {@link HttpPost} to perform.
	 * @return An {@link InputStream} of the response.
	 * @throws IOException If there's an IO error.
	 */
	private Response doHttpPost(HttpPost httpPost) throws IOException {
		// Execute HTTP Post Request
		final HttpResponse response = getHttpClient().execute(httpPost);

		return new com.alexgilleran.icesoap.request.impl.HttpResponse(
				response.getEntity().getContent(), response.getStatusLine().getStatusCode(),
				new com.alexgilleran.icesoap.request.impl.HttpResponse.Connection() {
					@Override
					public void close() throws IOException {
						response.getEntity().consumeContent();
					}
				}
		);
	}

	/**
	 * Gets the HTTP Client for this requester, building one with
	 * {@link #buildHttpClient()} if one has not already been built.
	 *
	 * @return The instance of {@link HttpClient}
	 */
	private HttpClient getHttpClient() {
		if (httpClient == null) {
			httpClient = buildHttpClient();
		}

		return httpClient;
	}

	/**
	 * Builds an Apache {@link HttpClient} from defaults.
	 *
	 * @return An implementation of {@link HttpClient}
	 */
	protected HttpClient buildHttpClient() {
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, DEFAULT_CONN_TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParameters, DEFAULT_SOCKET_TIMEOUT);

		SchemeRegistry schemeRegistry = getSchemeRegistry();

		ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(httpParameters, schemeRegistry);

		return new DefaultHttpClient(cm, httpParameters);
	}

	/**
	 * Builds a {@link SchemeRegistry}, which determines the
	 * {@link SocketFactory} that will be used for different ports.
	 * <p>
	 * This is very important because it will need to be overridden by an
	 * extension class if custom ports or factories (which are used for
	 * self-signed certificates) are to be used.
	 *
	 * @return A {@link SchemeRegistry} with the necessary port and factories
	 * registered.
	 */
	protected SchemeRegistry getSchemeRegistry() {
		SchemeRegistry schemeRegistry = new SchemeRegistry();

		schemeRegistry.register(new Scheme(HTTP_NAME, PlainSocketFactory.getSocketFactory(), DEFAULT_HTTP_PORT));
		schemeRegistry.register(new Scheme(HTTPS_NAME, SSLSocketFactory.getSocketFactory(), DEFAULT_HTTPS_PORT));

		return schemeRegistry;
	}

	/**
	 * Builds an {@link HttpPost} request.
	 *
	 * @param url        the URL to POST to
	 * @param envelope   The envelope to post
	 * @param soapAction SOAPAction for the header.
	 * @return An {@link HttpPost} object representing the supplied information.
	 * @throws UnsupportedEncodingException If the character encoding for the envelope is unsupported.
	 */
	protected HttpPost buildPostRequest(String url, SOAPEnvelope envelope, String soapAction)
			throws UnsupportedEncodingException {
		// Create a new HttpClient and Post Header
		HttpPost httppost = new HttpPost(url);

		httppost.setHeader(CONTENT_TYPE_LABEL, getXmlContentType(envelope.getMimeType(), envelope.getEncoding()));
		httppost.setHeader(HEADER_KEY_SOAP_ACTION, soapAction);

		HttpEntity entity = new StringEntity(envelope.toString(), envelope.getEncoding());

		httppost.setEntity(entity);
		return httppost;
	}

	/**
	 * Gets the content type to put in the HTTP header
	 *
	 * @param encoding The encoding being used (e.g. UTF-8)
	 * @return The full content type for the HTTP header.
	 */
	private String getXmlContentType(String mimeType, String encoding) {
		return String.format(XML_CONTENT_TYPE_FORMAT, mimeType, encoding);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setConnectionTimeout(int timeout) {
		HttpConnectionParams.setConnectionTimeout(getHttpClient().getParams(), timeout);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSocketTimeout(int timeout) {
		HttpConnectionParams.setSoTimeout(getHttpClient().getParams(), timeout);
	}
}
