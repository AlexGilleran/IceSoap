package com.alexgilleran.icesoap.envelope.impl;

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
public class PasswordSOAPEnvelope extends BaseSOAPEnvelope {
	/** Prefix for WSSE namespace */
	private static final String NS_PREFIX_WSSE = "wsse";
	/** URI for WSSE namespace */
	private static final String NS_URI_WSSE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
	/** The WSSE security node */
	protected XMLNode securityNode;

	/**
	 * Instantiates a new password envelope, creating a default envelope to wrap
	 * 
	 * @param username
	 *            The username to put in the security header
	 * @param password
	 *            The password to put in the security header
	 */
	public PasswordSOAPEnvelope(String username, String password) {
		super();

		setupWSSENode(username, password);
	}

	private void setupWSSENode(String username, String password) {
		securityNode = getHeader().addNode(NS_URI_WSSE, "Security");
		securityNode.declarePrefix(NS_PREFIX_WSSE, NS_URI_WSSE);
		securityNode.addAttribute(BaseSOAPEnvelope.NS_URI_SOAPENV,
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
	 * @return the UsernameToken element.
	 */
	protected XMLNode getUserNameToken(String username, String password) {
		XMLNode usernameToken = new XMLNodeImpl(NS_URI_WSSE, "UsernameToken");
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((securityNode == null) ? 0 : securityNode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PasswordSOAPEnvelope other = (PasswordSOAPEnvelope) obj;
		if (securityNode == null) {
			if (other.securityNode != null)
				return false;
		} else if (!securityNode.equals(other.securityNode))
			return false;
		return true;
	}
}
