package com.alexgilleran.icesoap.xpath.elements;

public class SingleSlashXPElement extends NodeXPElement {
	public SingleSlashXPElement(String name,
			BaseXPElement previousElement) {
		super(name, previousElement);
	}

	@Override
	public boolean matches(BaseXPElement otherElement) {
		if (!super.matches(otherElement))
		{
			return false;
		}

		if (this.isFirstElement()) {

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
