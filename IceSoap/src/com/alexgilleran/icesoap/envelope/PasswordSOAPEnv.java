package com.alexgilleran.icesoap.envelope;

import com.alexgilleran.icesoap.xml.XMLNode;
import com.alexgilleran.icesoap.xml.XMLTextElement;
import com.alexgilleran.icesoap.xml.impl.XMLNodeImpl;

/**
 * Example envelope - adds a WSSE Security tag that specifies a username and
 * password for the web service
 * 
 * @author Alex Gilleran
 * 
 */
public class PasswordSOAPEnv extends BaseSOAPEnvDecorator {

	private static final String NS_PREFIX_WSSE = "wsse";
	private static final String NS_URI_WSSE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";

	protected XMLNode securityNode;

	/**
	 * Instantiates a new password envelope, specifying username and password
	 * 
	 * @param wrappedEnvelope
	 *            SOAPEnv to wrap
	 * @param username
	 *            The username to put in the security header
	 * @param password
	 *            The password to put in the security header
	 */
	public PasswordSOAPEnv(SOAPEnv wrappedEnvelope, String username,
			String password) {
		super(wrappedEnvelope);

		securityNode = getWrappedEnvelope().getHeader().addNode(NS_URI_WSSE,
				"Security");
		securityNode.declarePrefix(NS_PREFIX_WSSE, NS_URI_WSSE);
		securityNode.addAttribute(ConcreteSOAPEnv.NS_URI_SOAPENV,
				"mustUnderstand", "1");

		securityNode.addElement(getUserNameToken(username, password));
	}

	/**
	 * Gets a UsernameToken element, specifying username and password
	 * 
	 * @param username
	 *            The username to add
	 * @param password
	 *            The password to add
	 * @return
	 */
	protected XMLNodeImpl getUserNameToken(String username, String password) {
		XMLNodeImpl usernameToken = new XMLNodeImpl(NS_URI_WSSE,
				"UsernameToken");
		usernameToken.addTextElement(NS_URI_WSSE, "Username", username);

		XMLTextElement passwordLeaf = usernameToken.addTextElement(NS_URI_WSSE,
				"Password", password);
		passwordLeaf
				.addAttribute(
						null,
						"type",
						"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");
		return usernameToken;
	}
}
