package com.alexgilleran.icesoap.xpath.elements;

public class AttributeXPElement extends SingleSlashXPElement {

	public AttributeXPElement(String name, BaseXPElement previousElement) {
		super(name, previousElement);
	}

	@Override
	public boolean matches(BaseXPElement otherElement)
	{
		if (!super.matches(otherElement))
		{
			return false;
		}
		
		if (!otherElement.getClass().isAssignableFrom(this.getClass()))
		{
			return false;
		}
		
		return true;
	}
	
	@Override
	public String getPredicate(String predicateName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringBuilder toStringBuilder() {
		// TODO Auto-generated method stub
		return null;
	}

}
