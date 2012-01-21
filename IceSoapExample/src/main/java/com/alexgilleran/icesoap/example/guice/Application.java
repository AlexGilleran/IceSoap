package com.alexgilleran.icesoap.example.guice;

import java.util.List;

import roboguice.application.RoboApplication;

import com.google.inject.Module;

/**
 * This is a RoboGuice-specific class that controls what modules are injected.
 * If you use the architecture of this app for your own, you'll want to use this
 * class to switch around the implementation modules based on whether you're
 * testing (in which case you'd mock things out) or in production.
 * 
 * @author Alex Gilleran
 * 
 */
public class Application extends RoboApplication {
	protected void addApplicationModules(List<Module> modules) {
		modules.add(new StandardModule());
	}
}
