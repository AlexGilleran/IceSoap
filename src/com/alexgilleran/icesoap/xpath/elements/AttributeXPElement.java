package com.alexgilleran.icesoap.xpath.elements;

public class AttributeXPElement extends SingleSlashXPElement {

	public AttributeXPElement(String name, BaseXPElement previousElement) {
		super(name, previousElement);
	}

	@Override
	public boolean matches(XPElement otherElement) {
		if (!super.matches(otherElement)) {
			return false;
		}

		if (!otherElement.getClass().isAssignableFrom(this.getClass())) {
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
}
