package com.alexgilleran.icesoap.xpath.elements;

public class AttributeXPathElement extends SingleSlashXPathElement {

	public AttributeXPathElement(String name, XPathElement previousElement) {
		super(name, previousElement);
	}

	@Override
	public boolean matches(XPathElement otherElement) {
		if (!super.matches(otherElement)) {
			return false;
		}

		if (!otherElement.isAttribute()) {
			return false;
		}

		return true;
	}

	@Override
	public String getPredicate(String predicateName) {
		// Do nothing

		return null;
	}

	@Override
	public void addPredicate(String name, String value) {
		// Do nothing
	}

	@Override
	public String getName() {
		return "@" + super.getName();
	}

	@Override
	public boolean isAttribute() {
		return true;
	}
}
