package com.alexgilleran.icesoap.example.guice;

import roboguice.config.AbstractAndroidModule;

import com.alexgilleran.icesoap.example.dao.DictionaryRequestFactory;
import com.alexgilleran.icesoap.example.dao.DictionaryRequestFactoryImpl;

/**
 * The standard (non-mock or anything else) module for the App.
 * 
 * @author Alex Gilleran
 * 
 */
public class StandardModule extends AbstractAndroidModule {

	@Override
	protected void configure() {
		bind(DictionaryRequestFactory.class).to(
				DictionaryRequestFactoryImpl.class);
	}
}
