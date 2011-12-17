package com.alexgilleran.icesoap.parser;

import java.util.List;

public interface IceSoapListParser<T> extends IceSoapParser<List<T>> {

	/**
	 * Registers an observer with the parser - any object passed to this method
	 * will have its {@link ItemObserver#onNewItem(Object)} method called every
	 * time a new object is parsed.
	 * 
	 * @param observer
	 *            The observer to register.
	 */
	void registerItemObserver(ItemObserver<T> observer);

	/**
	 * De-registers an observer that has previously been registered.
	 * 
	 * @param observer
	 *            The observer instance to de-register.
	 */
	void deregisterItemObserver(ItemObserver<T> observer);
}