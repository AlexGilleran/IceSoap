package com.alexgilleran.icesoap.request.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.alexgilleran.icesoap.envelope.SOAPEnvelope;
import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.request.SOAPRequester;

/**
 * Singleton implementation of {@link SOAPRequester}, using the Apache HTTP
 * Client
 * 
 * @author Alex Gilleran
 * 
 */
public class ApacheSOAPRequester implements SOAPRequester {
	/** Soap action to use if none is specified. */
	private static final String BLANK_SOAP_ACTION = "";
	/** Port for HTTPS communication */
	private static final int DEFAULT_HTTPS_PORT = 443;
	/** Port for HTTP communication */
	private static final int DEFAULT_HTTP_PORT = 80;
	/** Name of HTTPS */
	private static final String HTTPS_NAME = "https";
	/** Name of HTTP */
	private static final String HTTP_NAME = "http";
	/** Status returned if SOAP Request has executed successfully */
	private static final int HTTP_OK_STATUS = 200;
	/** HTTP content type submitted in HTTP POST request for SOAP calls */
	private static final String XML_CONTENT_TYPE = "text/xml; charset=UTF-8";
	/** Label for content-type header */
	private static final String CONTENT_TYPE_LABEL = "Content-type";
	/** Key for SOAP action header */
	private static final String HEADER_KEY_SOAP_ACTION = "SOAPAction";
	/** Timeout for making a connection */
	private static final int DEFAULT_CONN_TIMEOUT = 5000;
	/** Timeout for recieving data */
	private static final int DEFAULT_SOCKET_TIMEOUT = 20000;

	/** Apache HTTP Client for making HTTP requests */
	private HttpClient httpClient = buildHttpClient();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Response doSoapRequest(SOAPEnvelope envelope, String targetUrl)
			throws SOAPException {
		return doSoapRequest(envelope, targetUrl, BLANK_SOAP_ACTION);
	}

	/**
	 * {@inheritDoc}
	 */
	public Response doSoapRequest(SOAPEnvelope envelope, String url,
			String soapAction) throws SOAPException {
		try {
			return doHttpPost(buildPostRequest(url, envelope.toString(),
					soapAction));

			// Wrap any exceptions and rethrow.
		} catch (IOException e) {
			throw new SOAPException(e);
		}
	}

	/**
	 * Performs an HTTP POST request
	 * 
	 * @param httpPost
	 *            The {@link HttpPost} to perform.
	 * @return An {@link InputStream} of the response.
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws SOAPException
	 */
	private Response doHttpPost(HttpPost httpPost)
			throws ClientProtocolException, IOException, SOAPException {

		// Execute HTTP Post Request
		HttpResponse response = httpClient.execute(httpPost);

		int status = response.getStatusLine().getStatusCode();

		if (status != HTTP_OK_STATUS) {
			throw new SOAPException("HTTP Request Failure: "
					+ response.getStatusLine().toString());
		}

		HttpEntity res = new BufferedHttpEntity(response.getEntity());

		return new Response(res.getContent(), status);
	}

	/**
	 * Builds an Apache {@link HttpClient} from defaults.
	 * 
	 * @return An implementation of {@link HttpClient}
	 */
	private HttpClient buildHttpClient() {
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters,
				DEFAULT_CONN_TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParameters,
				DEFAULT_SOCKET_TIMEOUT);

		SchemeRegistry schemeRegistry = getSchemeRegistry();

		ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(
				httpParameters, schemeRegistry);

		return new DefaultHttpClient(cm, httpParameters);
	}

	protected SchemeRegistry getSchemeRegistry() {
		SchemeRegistry schemeRegistry = new SchemeRegistry();

		schemeRegistry.register(new Scheme(HTTP_NAME, PlainSocketFactory
				.getSocketFactory(), DEFAULT_HTTP_PORT));
		schemeRegistry.register(new Scheme(HTTPS_NAME, SSLSocketFactory
				.getSocketFactory(), DEFAULT_HTTPS_PORT));
		return schemeRegistry;
	}

	/**
	 * Builds an {@link HttpPost} request.
	 * 
	 * @param url
	 *            the URL to POST to
	 * @param envelopeString
	 *            The envelope to post, as a serialized string.
	 * @param soapAction
	 *            SOAPAction for the header.
	 * @return An {@link HttpPost} object representing the supplied information.
	 * @throws UnsupportedEncodingException
	 */
	private HttpPost buildPostRequest(String url, String envelopeString,
			String soapAction) throws UnsupportedEncodingException {
		// Create a new HttpClient and Post Header
		HttpPost httppost = new HttpPost(url);

		httppost.setHeader(CONTENT_TYPE_LABEL, XML_CONTENT_TYPE);
		httppost.setHeader(HEADER_KEY_SOAP_ACTION, soapAction);

		HttpEntity entity = new StringEntity(envelopeString);

		httppost.setEntity(entity);
		return httppost;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setConnectionTimeout(int timeout) {
		HttpConnectionParams.setConnectionTimeout(httpClient.getParams(),
				timeout);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSocketTimeout(int timeout) {
		HttpConnectionParams.setSoTimeout(httpClient.getParams(), timeout);
	}
}
