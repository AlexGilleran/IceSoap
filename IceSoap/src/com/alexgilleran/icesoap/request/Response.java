package com.alexgilleran.icesoap.request;

import java.io.IOException;
import java.io.InputStream;

/**
 * Encapsulates the details of a response from an SOAP request.
 *
 * @author Alex Gilleran
 */
public interface Response {
	/**
	 * Gets the data in the response as a stream.
	 *
	 * @return the data in the response as a stream.
	 */
	InputStream getData();

	/**
	 * Gets the result of the response.
	 *
	 * @return The status code as a {@link Status}
	 */
	Status getStatus();

	/**
	 * If the transport method involves any kind of connection that needs to be closed, do so here and clean up.
	 */
	void closeConnection() throws IOException;

	enum Status {
		OK,
		SOAP_FAULT,
		OTHER_FAULT
	}
}
