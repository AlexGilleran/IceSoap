package com.alexgilleran.icesoap.observer;

import java.util.List;

import com.alexgilleran.icesoap.request.BaseListRequest;
import com.alexgilleran.icesoap.request.BaseRequest;

/**
 * 
 * An extension of {@link SOAPObserver} to deal with {@link BaseListRequest}s.
 * 
 * @author Alex Gilleran
 * 
 * @param <ReturnType>
 *            The type of the object that will be retrieved from this request.
 * @param <SOAPFaultType>
 *            The type of the class to use for SOAPFaults
 */
public interface BaseSOAPListObserver<ReturnType, SOAPFaultType> extends
		BaseSOAPObserver<List<ReturnType>, SOAPFaultType> {

	/**
	 * Called (on the UI thread) when a new list item is received and parsed
	 * form a running {@link BaseListRequest}.
	 * 
	 * @param request
	 *            The request that the item was parsed by
	 * @param item
	 *            The item instance.
	 */
	public abstract void onNewItem(
			BaseRequest<List<ReturnType>, SOAPFaultType> request, ReturnType item);

}