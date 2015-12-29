package com.alexgilleran.icesoap.request.impl;

import com.alexgilleran.icesoap.request.Response;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Alex Gilleran
 * @{link Response} implementation for HttpResponses.
 */
public class HttpResponse implements Response {
	/**
	 * The data response as a stream.
	 */
	private InputStream data;
	/**
	 * The HTTP status code as returned by the request.
	 */
	private int httpStatus;
	/**
	 * A connection to close.
	 */
	private Connection connection;

	/**
	 * Creates a new response.
	 *
	 * @param data       The data of the response.
	 * @param httpStatus The HTTP request code.
	 */
	public HttpResponse(InputStream data, int httpStatus, Connection connection) {
		this.data = data;
		this.httpStatus = httpStatus;
		this.connection = connection;
	}

	@Override
	public InputStream getData() {
		return data;
	}

	@Override
	public Status getStatus() {
		switch (httpStatus) {
			case HTTPDefaults.HTTP_OK_STATUS:
				return Status.OK;
			case HTTPDefaults.HTTP_ERROR_STATUS:
				return Status.SOAP_FAULT;
			default:
				return Status.OTHER_FAULT;
		}
	}

	@Override
	public void closeConnection() throws IOException {
		connection.close();
	}

	/**
	 * A wrapper around any kind of connection that needs to be closed after all data has finished being consumed.
	 */
	protected interface Connection {
		/**
		 * Close the connection.
		 */
		void close() throws IOException;
	}
}