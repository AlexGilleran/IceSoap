package com.alexgilleran.icesoap.xpath.elements;

public class RelativeXPathElement extends SingleSlashXPathElement {

	public RelativeXPathElement(String name, XPathElement previousElement) {
		super(name, previousElement);
	}

	@Override
	protected String getPrefix() {
		if (isFirstElement()) {
			return "";
		} else {
			return super.getPrefix();
		}
	}

	@Override
	public boolean matches(XPathElement otherElement) {
		if (isFirstElement()) {
			throw new RuntimeException(
					"Attempt was made to match against relative xpath '"
							+ this.toString()
							+ "' when the xpath was not attached to an absolute xpath.");
		}

		return super.matches(otherElement);
	}
}
