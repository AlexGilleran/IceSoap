/**
 * 
 */
package com.alexgilleran.icesoap.parser;

/**
 * @author Alex Gilleran
 * 
 */
public interface ParserObserver<E> {
	public void onNewItem(E item);
}
