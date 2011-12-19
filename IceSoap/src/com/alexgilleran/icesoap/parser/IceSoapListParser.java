package com.alexgilleran.icesoap.parser;

import java.util.List;

/**
 * Extension of {@link IceSoapParser} that allows the ability to register
 * special observers that will be called every time a new item is passed.
 * 
 * @author Alex Gilleran
 * 
 * @param <ReturnType>
 *            The type to return.
 */
public interface IceSoapListParser<ReturnType> extends
		IceSoapParser<List<ReturnType>> {

	/**
	 * Registers an observer with the parser - any object passed to this method
	 * will have its {@link ItemObserver#onNewItem(Object)} method called every
	 * time a new object is parsed.
	 * 
	 * @param observer
	 *            The observer to register.
	 */
	void registerItemObserver(ItemObserver<ReturnType> observer);

	/**
	 * De-registers an observer that has previously been registered.
	 * 
	 * @param observer
	 *            The observer instance to de-register.
	 */
	void deregisterItemObserver(ItemObserver<ReturnType> observer);
}