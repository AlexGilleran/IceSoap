package com.alexgilleran.icesoap.requester;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.util.Log;

import com.alexgilleran.icesoap.envelope.SOAPEnv;

public class SOAPRequesterImpl implements SOAPRequester {
	private static final int HTTP_OK_STATUS = 200;
	private static final String XML_CONTENT_TYPE = "text/xml; charset=UTF-8";
	private static final String CONTENT_TYPE_LABEL = "Content-type";
	private static final String HEADER_KEY_SOAP_ACTION = "SOAPAction";

	private static int connectionTimeout = 5000;
	private static int soTimeout = 6000;

	private static SOAPRequester INSTANCE;

	public static SOAPRequester getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SOAPRequesterImpl();
		}

		return INSTANCE;
	}

	private SOAPRequesterImpl() {

	}

	public InputStream doSoapRequest(SOAPEnv envelope, String soapAction)
			throws IOException {
		String sEntity = envelope.getSerializedString();

		HttpPost httppost = getPostRequest(soapAction, sEntity);
		Log.d("outxml", sEntity);
		return doHttpPost(httppost);
	}

	private InputStream doHttpPost(HttpPost httpPost) throws IOException,
			ClientProtocolException {

		try {
			// Execute HTTP Post Request
			HttpResponse response = getHttpClient().execute(httpPost);

			int status = response.getStatusLine().getStatusCode();

			if (status != HTTP_OK_STATUS) {
				throw new IOException("HTTP Request Failure: "
						+ response.getStatusLine().toString());
			}

			BufferedHttpEntity res = new BufferedHttpEntity(
					response.getEntity());

			return res.getContent();
		} catch (KeyManagementException e) {
			throw new RuntimeException(e);
		} catch (UnrecoverableKeyException e) {
			throw new RuntimeException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (KeyStoreException e) {
			throw new RuntimeException(e);
		}
	}

	private HttpClient getHttpClient() throws KeyManagementException,
			UnrecoverableKeyException, NoSuchAlgorithmException,
			KeyStoreException {
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters,
				connectionTimeout);
		HttpConnectionParams.setSoTimeout(httpParameters, soTimeout);

		SchemeRegistry schemeRegistry = new SchemeRegistry();

		schemeRegistry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", new EasySSLSocketFactory(),
				8006));
		// schemeRegistry.register(new Scheme("https",
		// new CustomSSLSocketFactory(), 443));

		ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(
				httpParameters, schemeRegistry);

		return new DefaultHttpClient(cm, httpParameters);
	}

	private HttpPost getPostRequest(String soapAction, String sEntity)
			throws UnsupportedEncodingException {
		// Create a new HttpClient and Post Header
		HttpPost httppost = new HttpPost(soapAction);

		httppost.setHeader(CONTENT_TYPE_LABEL, XML_CONTENT_TYPE);
		httppost.setHeader(HEADER_KEY_SOAP_ACTION, soapAction);

		HttpEntity entity = new StringEntity(sEntity);

		httppost.setEntity(entity);
		return httppost;
	}
}
