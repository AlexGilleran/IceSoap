package com.alexgilleran.icesoap.request.impl;

import com.alexgilleran.icesoap.envelope.SOAPEnvelope;
import com.alexgilleran.icesoap.request.Response;
import com.alexgilleran.icesoap.request.SOAPRequester;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Implementation of {@link SOAPRequester} that uses {@link HttpURLConnection}
 */
public class HUCSOAPRequester implements SOAPRequester, HTTPDefaults {
	private static final String POST_NAME = "POST";
	private int connTimeout = DEFAULT_CONN_TIMEOUT;
	private int socketTimeout = DEFAULT_SOCKET_TIMEOUT;

	@Override
	public Response doSoapRequest(SOAPEnvelope envelope, String targetUrl) throws IOException {
		return doSoapRequest(envelope, targetUrl, BLANK_SOAP_ACTION);
	}

	@Override
	public Response doSoapRequest(SOAPEnvelope envelope, String targetUrl, String soapAction) throws IOException {
		final URL url = new URL(targetUrl);
		final HttpURLConnection conn = buildHttpUrlConnection(url);
		final String envelopeStr = envelope.toString();

		try {
			byte[] envBytes = envelopeStr.getBytes(envelope.getEncoding());

			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setFixedLengthStreamingMode(envBytes.length);
			conn.setRequestMethod(POST_NAME);
			conn.setRequestProperty(CONTENT_TYPE_LABEL, String.format(XML_CONTENT_TYPE_FORMAT, envelope.getMimeType(), envelope.getEncoding()));
			conn.setRequestProperty(HEADER_KEY_SOAP_ACTION, soapAction);
			conn.setConnectTimeout(connTimeout);
			conn.setReadTimeout(socketTimeout);

			OutputStream out = conn.getOutputStream();
			out.write(envBytes);
			out.flush();
			out.close();

			return new HttpResponse(conn.getInputStream(), conn.getResponseCode(), new HttpResponse.Connection() {
				@Override
				public void close() {
					conn.disconnect();
				}
			});
		} catch (IOException e) {
			conn.disconnect();
			throw e;
		}
	}

	/**
	 * Builds an {@link HttpURLConnection} from the required URL. By default simply uses {@link URL#openConnection} and
	 * and casts to HttpURLConnection. Override this method in order to perform more advanced operations like casting
	 * to {@link javax.net.ssl.HttpsURLConnection} and providing your own socket factories to accept self-signed
	 * certificates, or only accept pinned certificates.
	 *
	 * @param url The URL to open a connection for.
	 * @return The opened connection.
	 * @throws IOException If some kind of I/O error happens while establishing the connection.
	 */
	protected HttpURLConnection buildHttpUrlConnection(URL url) throws IOException {
		return (HttpURLConnection) url.openConnection();
	}

	@Override
	public void setConnectionTimeout(int timeout) {
		connTimeout = timeout;
	}

	@Override
	public void setSocketTimeout(int timeout) {
		socketTimeout = timeout;
	}
}
