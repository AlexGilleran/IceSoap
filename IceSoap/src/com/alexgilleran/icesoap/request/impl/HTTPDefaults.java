package com.alexgilleran.icesoap.request.impl;

/**
 * Created by Alex on 2015-11-14.
 */
interface HTTPDefaults {
	/**
	 * Soap action to use if none is specified.
	 */
	String BLANK_SOAP_ACTION = "";
	/**
	 * Port for HTTPS communication.
	 */
	int DEFAULT_HTTPS_PORT = 443;
	/**
	 * Port for HTTP communicatio.n
	 */
	int DEFAULT_HTTP_PORT = 80;
	/**
	 * Name of HTTPS.
	 */
	String HTTPS_NAME = "https";
	/**
	 * Name of HTTP.
	 */
	String HTTP_NAME = "http";
	/**
	 * HTTP content type submitted in HTTP POST request for SOAP calls.
	 */
	String XML_CONTENT_TYPE_FORMAT = "%s; charset=%s";
	/**
	 * Label for content-type header.
	 */
	String CONTENT_TYPE_LABEL = "Content-type";
	/**
	 * Key for SOAP action header.
	 */
	String HEADER_KEY_SOAP_ACTION = "SOAPAction";
	/**
	 * Timeout for making a connection.
	 */
	int DEFAULT_CONN_TIMEOUT = 5000;
	/**
	 * Timeout for recieving data.
	 */
	int DEFAULT_SOCKET_TIMEOUT = 20000;
	/**
	 * Status returned if SOAP Request has executed successfully.
	 */
	int HTTP_OK_STATUS = 200;
	/**
	 * Status returned if there's an error that returns a soap fault.
	 */
	int HTTP_ERROR_STATUS = 500;
}
