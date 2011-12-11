package com.alexgilleran.icesoap.xpath.elements;


public abstract class BaseXPElement {

	private String name;
	private BaseXPElement previousElement;

	public BaseXPElement(String name, BaseXPElement previousElement) {
		this.name = name;
		this.previousElement = previousElement;
	}

	public BaseXPElement getPreviousElement() {
		return previousElement;
	}

	public String getName() {
		return name;
	}

	public boolean matches(BaseXPElement otherElement) {
		return equals(otherElement);
	}

	public abstract String getPredicate(String predicateName);

	public boolean isFirstElement() {
		return previousElement == null;
	}

	public abstract StringBuilder toStringBuilder();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		BaseXPElement other = (BaseXPElement) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}