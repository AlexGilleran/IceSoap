/**
 * 
 */
package com.alexgilleran.icesoap.parser;

/**
 * Defines classes that can be set up to receive new item events from a
 * {@link IceSoapListParser}
 * 
 * @author Alex Gilleran
 * 
 */
public interface ItemObserver<E> {
	/**
	 * Is called every time a new item is completely parsed.
	 * 
	 * Note that this <em>will not</em> be called for null (xsi:nil) items.
	 * 
	 * @param item
	 *            The new item.
	 */
	public void onNewItem(E item);
}
