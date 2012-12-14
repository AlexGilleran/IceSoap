package com.alexgilleran.icesoap.parser;

/**
 * Defines classes that can be set up to receive new item events from an
 * {@link IceSoapListParser}.
 * 
 * @author Alex Gilleran
 * 
 * @param <TypeToReturn>
 *            The type of object that will be returned by the parser.
 */
public interface ItemObserver<TypeToReturn> {
	/**
	 * This is called every time a new item is completely parsed.
	 * 
	 * Note that this <em>will not</em> be called for null (xsi:nil) items.
	 * 
	 * @param item
	 *            The newly parsed item.
	 */
	void onNewItem(TypeToReturn item);
}
