/**
 * 
 */
package com.alexgilleran.icesoap.parser;

/**
 * @author Alex Gilleran
 * 
 */
public interface ItemObserver<E> {
	public void onNewItem(E item);
}
