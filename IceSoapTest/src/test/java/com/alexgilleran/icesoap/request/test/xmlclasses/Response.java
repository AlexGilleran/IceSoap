package com.alexgilleran.icesoap.request.test.xmlclasses;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

@XMLObject("//Details")
public class Response {
	@XMLField("@id")
	private int responseId;
	@XMLField("TextField")
	private String textField;

	public Response() {

	}

	public Response(int responseId, String textField) {
		this.responseId = responseId;
		this.textField = textField;
	}

	public int getResponseId() {
		return responseId;
	}

	public void setResponseId(int responseId) {
		this.responseId = responseId;
	}

	public String getTextField() {
		return textField;
	}

	public void setTextField(String textField) {
		this.textField = textField;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + responseId;
		result = prime * result
				+ ((textField == null) ? 0 : textField.hashCode());
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
		Response other = (Response) obj;
		if (responseId != other.responseId)
			return false;
		if (textField == null) {
			if (other.textField != null)
				return false;
		} else if (!textField.equals(other.textField))
			return false;
		return true;
	}

}
