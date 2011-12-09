package com.alexgilleran.icesoap.observer;

public interface SOAPObserver<E> {
	public void onNewDaoItem(E item);
}
