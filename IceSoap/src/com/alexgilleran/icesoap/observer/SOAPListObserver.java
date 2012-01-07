/**
 * 
 */
package com.alexgilleran.icesoap.observer;

import java.util.List;

import com.alexgilleran.icesoap.request.ListRequest;
import com.alexgilleran.icesoap.request.Request;

/**
 * An extension of {@link SOAPObserver} to deal with {@link ListRequest}s.
 * 
 * @author Alex Gilleran
 * 
 */
public interface SOAPListObserver<ReturnType> extends
		SOAPObserver<List<ReturnType>> {
	/**
	 * Called (on the UI thread) when a new list item is received and parsed
	 * form a running {@link ListRequest}.
	 * 
	 * @param request
	 *            The request that the item was parsed by
	 * @param item
	 *            The item instance.
	 */
	public void onNewItem(Request<List<ReturnType>> request, ReturnType item);
}
