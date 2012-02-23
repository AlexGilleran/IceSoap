package com.alexgilleran.icesoap.request.test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
//<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:foot="http://footballpool.dataaccess.eu">
//<soapenv:Header/>
//<soapenv:Body>

public class SampleResponse {
	public static String FAULT_CODE = "faultCode";
	public static String FAULT_STRING = "faultString";
	public static String FAULT_ACTOR = "faultActor";

	private final static String SOAP11_FAULT = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:response=\"http://example.com/response\">"
			+ "<soapenv:Header/>"//
			+ "<soapenv:Body>"//
			+ "<soapenv:Fault>"//
			+ "<faultcode>" + FAULT_CODE + "</faultcode>"//
			+ "<faultstring>" + FAULT_STRING + "</faultstring>"//
			+ "<faultactor>" + FAULT_ACTOR + "</faultactor>"//
			+ "</soapenv:Fault>"//
			+ "</soapenv:Body>"//
			+ "</soapenv:Envelope>";//

	private final static String SINGLE_RESPONSE = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:response=\"http://example.com/response\">"
			+ "<soapenv:Header/>"//
			+ "<soapenv:Body>"//
			+ "<response:Response>"//
			+ "<response:Details id=\"1\">"//
			+ "<response:TextField>Text</response:TextField>"//
			+ "</response:Details>"//
			+ "</response:Response>"//
			+ "</soapenv:Body>"//
			+ "</soapenv:Envelope>";//

	private final static String LIST_RESPONSE = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:response=\"http://example.com/response\">"
			+ "<soapenv:Header/>"//
			+ "<soapenv:Body>"//
			+ "<response:Response>"//
			+ "<response:Details id=\"1\">"//
			+ "<response:TextField>Text1</response:TextField>"//
			+ "</response:Details>"//
			+ "<response:Details id=\"2\">"//
			+ "<response:TextField>Text2</response:TextField>"//
			+ "</response:Details>"//
			+ "<response:Details id=\"3\">"//
			+ "<response:TextField>Text3</response:TextField>"//
			+ "</response:Details>"//
			+ "<response:Details id=\"4\">"//
			+ "<response:TextField>Text4</response:TextField>"//
			+ "</response:Details>"//
			+ "</response:Response>"//
			+ "</soapenv:Body>"//
			+ "</soapenv:Envelope>";//

	public static InputStream getSingleResponse() {
		return new ByteArrayInputStream(SINGLE_RESPONSE.getBytes());
	}

	public static InputStream getListResponse() {
		return new ByteArrayInputStream(LIST_RESPONSE.getBytes());
	}

	public static InputStream getSoap11Fault() {
		return new ByteArrayInputStream(SOAP11_FAULT.getBytes());
	}
}
