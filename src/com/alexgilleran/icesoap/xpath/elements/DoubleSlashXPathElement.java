package com.alexgilleran.icesoap.xpath.elements;

public class DoubleSlashXPathElement extends BaseXPathElement {

	public DoubleSlashXPathElement(String name, XPathElement previousElement) {
		super(name, previousElement);
	}

	@Override
	protected String getPrefix() {
		return "//";
	}

	@Override
	public boolean matches(XPathElement otherElement) {
		if (!super.matches(otherElement)) {
			return false;
		}
		
		// If this is the first element, anything coming before is fine
		if (!this.isFirstElement()) {
			// This element starts with '//' and has previous elements -
			// loop through all the previous elements that the other element
			// has, to see if any of them match this node's previous element

			XPathElement thisPreviousElement = otherElement
					.getPreviousElement();

			while (thisPreviousElement != null) {
				if (this.getPreviousElement().matches(thisPreviousElement)) {
					return true;
				}

				thisPreviousElement = thisPreviousElement.getPreviousElement();
			}

			return false;
		}

		return true;
	}
}
