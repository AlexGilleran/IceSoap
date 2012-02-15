/**
 * 
 */
package com.alexgilleran.icesoap.parser;

import com.alexgilleran.icesoap.request.BaseRequest;

/**
 * Defines classes that can be set up to recieve new item events from a
 * {@link IceSoapListParser}
 * 
 * Note that this is distinct from the {@link ItemObserver} class used by
 * parsers, as it is tightly coupled to {@link BaseRequest}.
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
