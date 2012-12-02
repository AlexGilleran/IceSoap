package com.alexgilleran.icesoap.soapfault;

import java.util.List;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

/**
 * Represents a SOAP 1.2 Fault.
 * 
 * @author Alex Gilleran
 * 
 */
@XMLObject("//Fault")
public class SOAP12Fault {
	@XMLField("Code/Value")
	private String code;
	@XMLField("Code/Subcode/Value")
	private String subCode;
	@XMLField("Reason/Text")
	private List<FaultReason> reasons;
	@XMLField("Node")
	private String node;
	@XMLField("Role")
	private String role;

	/**
	 * @return the fault code.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return The fault subcode
	 */
	public String getSubCode() {
		return subCode;
	}

	/**
	 * @return A list of reasons for the fault - each reason for a different
	 *         language.
	 */
	public List<FaultReason> getReasons() {
		return reasons;
	}

	/**
	 * @return The node value of the fault.
	 */
	public String getNode() {
		return node;
	}

	/**
	 * @return The role value of the fault.
	 */
	public String getRole() {
		return role;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((node == null) ? 0 : node.hashCode());
		result = prime * result + ((reasons == null) ? 0 : reasons.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((subCode == null) ? 0 : subCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SOAP12Fault other = (SOAP12Fault) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (node == null) {
			if (other.node != null)
				return false;
		} else if (!node.equals(other.node))
			return false;
		if (reasons == null) {
			if (other.reasons != null)
				return false;
		} else if (!reasons.equals(other.reasons))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (subCode == null) {
			if (other.subCode != null)
				return false;
		} else if (!subCode.equals(other.subCode))
			return false;
		return true;
	}
}
