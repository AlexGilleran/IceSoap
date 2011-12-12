package com.alexgilleran.icesoap.xpath.elements;

public class DoubleSlashXPElement extends NodeXPElement {

	public DoubleSlashXPElement(String name, XPathElement previousElement) {
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

		if (this.isFirstElement()) {
			if (!otherElement.isFirstElement()) {
				// This element is the beginning element of a non-doubleslash
				// xpath, but the other xpath has previous elements still.
				return false;
			}
		} else {
			// This element starts with '//' and has previous elements -
			// loop through all the previous elements that the other element
			// has, to see if any of them match this node's previous element
			XPathElement thisPrevElement = otherElement.getPreviousElement();

			while (thisPrevElement != null) {
				if (thisPrevElement.matches(otherElement.getPreviousElement())) {
					return true;
				} else {
					thisPrevElement = thisPrevElement.getPreviousElement();
				}
			}

			return false;
		}

		return true;
	}
}
