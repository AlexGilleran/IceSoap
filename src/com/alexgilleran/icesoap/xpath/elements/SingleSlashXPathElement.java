package com.alexgilleran.icesoap.xpath.elements;

public class SingleSlashXPathElement extends BaseXPathElement {
	public SingleSlashXPathElement(String name, XPathElement previousElement) {
		super(name, previousElement);
	}

	@Override
	public boolean matches(XPathElement otherElement) {
		if (!super.matches(otherElement)) {
			return false;
		}

		if (this.isFirstElement() != otherElement.isFirstElement()) {
			return false;
		}

		if (!this.isFirstElement()) {
			// Previous element is not null and this isn't a '//' element -
			// see if the previous elements of both xpaths match
			return getPreviousElement().matches(
					otherElement.getPreviousElement());
		}

		return true;
	}

	@Override
	protected String getPrefix() {
		return "/";
	}
}
