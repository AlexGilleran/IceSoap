package com.alexgilleran.icesoap.example.guice;

import roboguice.config.AbstractAndroidModule;

import com.alexgilleran.icesoap.example.dao.RequestFactory;
import com.alexgilleran.icesoap.example.dao.RequestFactoryImpl;

public class StandardModule extends AbstractAndroidModule {

	@Override
	protected void configure() {
		bind(RequestFactory.class).to(RequestFactoryImpl.class);
	}
}
