/**
 * 
 */
package com.alexgilleran.icesoap.parser;

/**
 * Defines classes that can be set up to recieve new item events from a
 * {@link IceSoapListParser}
 * 
 * @author Alex Gilleran
 * 
 */
public interface ItemObserver<E> {
	/**
	 * Is called every time a new item is completely parsed.
	 * 
	 * @param item
	 *            The new item.
	 */
	public void onNewItem(E item);
}
