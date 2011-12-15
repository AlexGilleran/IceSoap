/**
 * 
 */
package com.alexgilleran.icesoap.observer;

import java.util.List;

import com.alexgilleran.icesoap.request.Request;

/**
 * @author Alex Gilleran
 * 
 */
public interface SOAPListObserver<E> extends SOAPObserver<List<E>> {
	public void onNewItem(Request<List<E>> request, E item);
}
