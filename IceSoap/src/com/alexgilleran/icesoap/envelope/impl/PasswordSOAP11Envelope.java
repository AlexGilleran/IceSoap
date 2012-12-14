package com.alexgilleran.icesoap.envelope.impl;

import com.alexgilleran.icesoap.xml.XMLParentNode;
import com.alexgilleran.icesoap.xml.XMLTextNode;
import com.alexgilleran.icesoap.xml.impl.XMLParentNodeImpl;

/**
 * Example SOAP 1.1 envelope - adds a WSSE Security tag that specifies a
 * username and password for the web service.
 * 
 * @author Alex Gilleran
 * 
 */
public class PasswordSOAP11Envelope extends BaseSOAP11Envelope {
	/** Prefix for WSSE namespace. */
	private static final String NS_PREFIX_WSSE = "wsse";
	/** URI for WSSE namespace. */
	private static final String NS_URI_WSSE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
	/** The WSSE security node. */
	protected XMLParentNode securityNode;

	/**
	 * Instantiates a new password envelope, creating a default envelope to
	 * wrap.
	 * 
	 * @param username
	 *            The username to put in the security header.
	 * @param password
	 *            The password to put in the security header.
	 */
	public PasswordSOAP11Envelope(String username, String password) {
		super();

		setupWSSENode(username, password);
	}

	/**
	 * Sets up the WSSE security node in the envelope header.
	 * 
	 * @param username
	 *            The username to include.
	 * @param password
	 *            The password to include.
	 */
	private void setupWSSENode(String username, String password) {
		securityNode = getHeader().addNode(NS_URI_WSSE, "Security");
		securityNode.declarePrefix(NS_PREFIX_WSSE, NS_URI_WSSE);
		securityNode.addAttribute(BaseSOAP11Envelope.NS_URI_SOAPENV, "mustUnderstand", "1");

		securityNode.addElement(getUserNameToken(username, password));
	}

	/**
	 * Gets a UsernameToken element, specifying username and password.
	 * 
	 * @param username
	 *            The username to add.
	 * @param password
	 *            The password to add.
	 * @return the UsernameToken element.
	 */
	protected XMLParentNode getUserNameToken(String username, String password) {
		XMLParentNode usernameToken = new XMLParentNodeImpl(NS_URI_WSSE, "UsernameToken");
		usernameToken.addTextNode(NS_URI_WSSE, "Username", username);

		XMLTextNode passwordLeaf = usernameToken.addTextNode(NS_URI_WSSE, "Password", password);
		passwordLeaf.addAttribute(null, "type",
				"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");
		return usernameToken;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((securityNode == null) ? 0 : securityNode.hashCode());
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
		PasswordSOAP11Envelope other = (PasswordSOAP11Envelope) obj;
		if (securityNode == null) {
			if (other.securityNode != null)
				return false;
		} else if (!securityNode.equals(other.securityNode))
			return false;
		return true;
	}
}
