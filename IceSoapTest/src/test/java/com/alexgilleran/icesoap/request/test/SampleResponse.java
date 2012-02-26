package com.alexgilleran.icesoap.request.test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class SampleResponse {
	public static String SOAP11_FAULT_CODE = "faultCode";
	public static String SOAP11_FAULT_STRING = "faultString";
	public static String SOAP11_FAULT_ACTOR = "faultActor";

	public static String SOAP12_FAULT_CODE = "SOAP-1_2-ENV:Sender";
	public static String SOAP12_FAULT_SUBCODE = "sqlsoapfaultcode:InvalidXml";
	public static String SOAP12_FAULT_REASON_LANG = "en-US";
	public static String SOAP12_FAULT_REASON = "There was an error in the incoming SOAP request packet:  Sender, InvalidXml";
	public static String SOAP12_FAULT_NODE = "http://MyServer:80/sql";
	public static String SOAP12_FAULT_ROLE = "http://schemas.microsoft.com/sqlserver/2004/SOAP";

	private final static String SOAP12_FAULT = "<SOAP-1_2-ENV:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:SOAP-1_2-ENV=\"http://www.w3.org/2003/05/soap-envelope\">"
			+ "<SOAP-1_2-ENV:Header/>"
			+ "<SOAP-1_2-ENV:Body>"
			+ "<SOAP-1_2-ENV:Fault xmlns:sqlresultstream=\"http://schemas.microsoft.com/sqlserver/2004/SOAP/SqlMessage\" xmlns:sqlmessage=\"http://schemas.microsoft.com/sqlserver/2004/SOAP/SqlMessage\" xmlns:sqlsoapfaultcode=\"http://schemas.microsoft.com/sqlserver/2004/SOAP/SqlSoapFaultCode\">"
			+ "<SOAP-1_2-ENV:Code>" + "<SOAP-1_2-ENV:Value>"
			+ SOAP12_FAULT_CODE
			+ "</SOAP-1_2-ENV:Value>"
			+ "<SOAP-1_2-ENV:Subcode>"
			+ "<SOAP-1_2-ENV:Value>"
			+ SOAP12_FAULT_SUBCODE
			+ "</SOAP-1_2-ENV:Value>"
			+ "</SOAP-1_2-ENV:Subcode>"
			+ "</SOAP-1_2-ENV:Code>"
			+ "<SOAP-1_2-ENV:Reason>"
			+ "<SOAP-1_2-ENV:Text xml:lang=\""
			+ SOAP12_FAULT_REASON_LANG
			+ "\">"
			+ SOAP12_FAULT_REASON
			+ "</SOAP-1_2-ENV:Text>"
			+ "</SOAP-1_2-ENV:Reason>"
			+ "<SOAP-1_2-ENV:Node>"
			+ SOAP12_FAULT_NODE
			+ "</SOAP-1_2-ENV:Node>"
			+ "<SOAP-1_2-ENV:Role>"
			+ SOAP12_FAULT_ROLE
			+ "</SOAP-1_2-ENV:Role>"
			+ "<SOAP-1_2-ENV:Detail>"
			+ "<sqlresultstream:SqlMessage xsi:type=\"sqlmessage:SqlMessage\">"
			+ "<sqlmessage:Class>16</sqlmessage:Class>"
			+ "<sqlmessage:LineNumber>0</sqlmessage:LineNumber>"
			+ "<sqlmessage:Message>XML parsing: line 3, character 0, incorrect document syntax</sqlmessage:Message>"
			+ "<sqlmessage:Number>9422</sqlmessage:Number>"
			+ "<sqlmessage:Source>Microsoft-SQL/9.0</sqlmessage:Source>"
			+ "<sqlmessage:State>1</sqlmessage:State>"
			+ "</sqlresultstream:SqlMessage>"
			+ "</SOAP-1_2-ENV:Detail>"
			+ "</SOAP-1_2-ENV:Fault>" + "</SOAP-1_2-ENV:Body>"//
			+ "</SOAP-1_2-ENV:Envelope>";

	private final static String SOAP11_FAULT = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:response=\"http://example.com/response\">"
			+ "<soapenv:Header/>"//
			+ "<soapenv:Body>"//
			+ "<soapenv:Fault>"//
			+ "<faultcode>" + SOAP11_FAULT_CODE + "</faultcode>"//
			+ "<faultstring>" + SOAP11_FAULT_STRING + "</faultstring>"//
			+ "<faultactor>" + SOAP11_FAULT_ACTOR + "</faultactor>"//
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

	public static InputStream getSoap12Fault() {
		return new ByteArrayInputStream(SOAP12_FAULT.getBytes());
	}
}
